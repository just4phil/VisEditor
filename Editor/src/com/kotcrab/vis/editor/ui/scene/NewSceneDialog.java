/*
 * Copyright 2014-2015 Pawel Pastuszak
 *
 * This file is part of VisEditor.
 *
 * VisEditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VisEditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VisEditor.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kotcrab.vis.editor.ui.scene;

import com.kotcrab.vis.editor.App;
import com.kotcrab.vis.editor.event.StatusBarEvent;
import com.kotcrab.vis.editor.module.project.FileAccessModule;
import com.kotcrab.vis.editor.module.project.SceneIOModule;
import com.kotcrab.vis.runtime.scene.SceneViewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.OrderedMap;
import com.kotcrab.vis.ui.FormInputValidator;
import com.kotcrab.vis.ui.FormValidator;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.VisTable;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidableTextField;
import com.kotcrab.vis.ui.widget.VisWindow;

public class NewSceneDialog extends VisWindow {
	private VisValidableTextField nameTextField;
	private VisValidableTextField pathTextField;
	private VisSelectBox<String> viewportModeSelectBox;

	private VisLabel errorLabel;

	private VisTextButton cancelButton;
	private VisTextButton createButton;

	private SceneIOModule sceneIO;

	private FileHandle visFolder;

	private OrderedMap<String, SceneViewport> viewportMap;

	public NewSceneDialog (FileAccessModule fileAccess, SceneIOModule sceneIOModule) {
		super("New Scene");
		addCloseButton();
		closeOnEscape();
		setModal(true);

		sceneIO = sceneIOModule;
		visFolder = fileAccess.getVisFolder();

		viewportMap = new OrderedMap<String, SceneViewport>();
		SceneViewport[] values = SceneViewport.values();
		for (int i = 0; i < values.length; i++)
			viewportMap.put(values[i].toListString(), values[i]);

		createUI();
		createListeners();
		createValidators();

		pack();
		centerWindow();
	}

	private void createUI () {
		nameTextField = new VisValidableTextField();
		pathTextField = new VisValidableTextField("/assets/scene/");
		viewportModeSelectBox = new VisSelectBox<String>();
		viewportModeSelectBox.setItems(viewportMap.keys().toArray());

		errorLabel = new VisLabel();
		errorLabel.setColor(Color.RED);

		TableUtils.setSpaceDefaults(this);
		columnDefaults(0).left();
		columnDefaults(1).width(300);

		row().padTop(4);

		VisTable fileFieldTable = new VisTable(true);
		fileFieldTable.add(nameTextField).expand().fill();
		fileFieldTable.add(new VisLabel(".scene"));

		add(new VisLabel("File name"));
		add(fileFieldTable);
		row();

		add(new VisLabel("Path"));
		add(pathTextField);
		row();

		add(new VisLabel("Viewport"));
		add(viewportModeSelectBox);
		row();

		VisTable buttonTable = new VisTable(true);
		buttonTable.defaults().minWidth(70);

		cancelButton = new VisTextButton("Cancel");
		createButton = new VisTextButton("Create");
		createButton.setDisabled(true);

		buttonTable.add(errorLabel).fill().expand();
		buttonTable.add(cancelButton);
		buttonTable.add(createButton);

		add(buttonTable).colspan(2).fill().expand();
		padBottom(5);
	}

	private void createListeners () {
		cancelButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				fadeOut();
			}
		});

		createButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				FileHandle targetFile = Gdx.files.absolute(pathTextField.getText()).child(nameTextField.getText() + ".scene");
				sceneIO.create(targetFile, viewportMap.get(viewportModeSelectBox.getSelected()));
				App.eventBus.post(new StatusBarEvent("Scene created: " + targetFile.path().substring(1)));
				fadeOut();
			}
		});
	}

	private void createValidators () {
		FormValidator validator = new FormValidator(createButton, errorLabel);
		validator.notEmpty(nameTextField, "Name cannot be empty!");
		validator.notEmpty(pathTextField, "Path cannot be empty!");

		validator.fileExists(pathTextField, visFolder, "Path does not exist!");

		validator.custom(nameTextField, new FormInputValidator("That scene already exists!") {
			@Override
			public boolean validateInput (String input) {
				FileHandle sceneFile = visFolder.child(pathTextField.getText()).child(input + ".scene");
				setResult(!sceneFile.exists());

				return super.validateInput(input);
			}
		});
	}
}
