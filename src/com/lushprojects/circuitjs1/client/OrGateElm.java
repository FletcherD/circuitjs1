/*    
    Copyright (C) Paul Falstad and Iain Sharp

    This file is part of CircuitJS1.

    CircuitJS1 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    CircuitJS1 is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CircuitJS1.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lushprojects.circuitjs1.client;

//import java.awt.*;
//import java.util.StringTokenizer;

class OrGateElm extends GateElm {
	public OrGateElm(int xx, int yy) { super(xx, yy); }
	public OrGateElm(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
		super(xa, ya, xb, yb, f, st);
	}
	
	void draw(Graphics g) {
		super.draw(g);
		g.setLineWidth(3.0);
		g.setFillColor(Color.black);
		g.setStrokeColor(needsHighlight() ? selectColor : lightGrayColor);
		if (this instanceof XorGateElm) {	// draw extra line on back of gate for XOR
			g.context.save();
			g.setDrawRegionScaled(lead1, lead2);
			g.context.scale(1, heightScale);
			g.context.beginPath();
			g.context.translate(-0.125,  0);
			g.context.moveTo(-0.1, -0.5);
			g.context.arcTo(0.1, 0.0, -0.1, 0.5, 1);	// back curve
			g.context.lineTo(-0.1, 0.5);
			g.context.restore();
			g.context.stroke();		
		}
		g.context.save();
		g.setDrawRegionScaled(lead1, lead2);
		g.context.scale(1, heightScale);
		g.context.beginPath();
		g.context.moveTo(-0.1, -0.5);
		g.context.arcTo(0.1, 0.0, -0.1, 0.5, 1);	// back curve
		g.context.lineTo(-0.1, 0.5);
		g.context.arcTo(0.6, 0.5, 1.0, 0.0, 1.0); // bottom curve
		g.context.lineTo(1.0, 0.0);
		g.context.arcTo(0.6, -0.5, 0.4, -0.5, 1.0); // top curve
		g.context.lineTo(0.0, -0.5);
		g.context.closePath();
		g.context.restore();
		g.context.fill();
		g.context.stroke();
		if (isInverting()) {	// draw dot on tip for inverting.
			g.context.save();
			g.setDrawRegionScaled(lead1, lead2);
			double len = Point.distance(lead1, lead2);
			g.context.beginPath();
			g.context.arc(1.0 + 4/len, 0.0, 4/len, 0, 2*pi);
			g.context.closePath();
			g.context.restore();
			g.context.fill();
			g.context.stroke();
		}
	}

	String getGateName() { return "OR gate"; }
	boolean calcFunction() {
		int i;
		boolean f = false;
		for (i = 0; i != inputCount; i++)
			f |= getInput(i);
		return f;
	}
	int getDumpType() { return 152; }
	int getShortcut() { return '3'; }
}
