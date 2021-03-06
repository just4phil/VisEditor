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

package com.kotcrab.vis.editor.module.scene;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteData {
	public float x, y;
	public float width, height;
	public float originX, originY;
	public float rotation;
	public float scaleX = 1, scaleY = 1;

	public void save (Sprite sprite) {
		x = sprite.getX();
		y = sprite.getY();

		width = sprite.getWidth();
		height = sprite.getHeight();

		originX = sprite.getOriginX();
		originY = sprite.getOriginY();

		rotation = sprite.getRotation();

		scaleX = sprite.getScaleX();
		scaleY = sprite.getScaleY();
	}

	public void load (Sprite sprite) {
		sprite.setPosition(x,y);
		sprite.setSize(width, height);
		sprite.setOrigin(originX, originY);
		sprite.setRotation(rotation);
		sprite.setScale(scaleX, scaleY);
	}
}
