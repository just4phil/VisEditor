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

package com.kotcrab.vis.editor.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.VisTable;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.editor.module.project.Project;
import com.kotcrab.vis.editor.ui.tab.Tab;
import com.kotcrab.vis.editor.ui.tab.TabViewMode;

public class ProjectInfoTab extends Tab {
	private Table content;

	public ProjectInfoTab (Project project) {
		super(false, false);
		content = new VisTable(true);

		content.add(new VisLabel("Some project info:"));
		content.row();
		content.add(new VisLabel("Root: " + project.root));
	}

	@Override
	public String getButtonText () {
		return "Project Info";
	}

	@Override
	public Table getContentTable () {
		return content;
	}

	@Override
	public TabViewMode getViewMode () {
		return TabViewMode.WITH_PROJECT_ASSETS_MANAGER;
	}
}
