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

import com.google.gwt.canvas.dom.client.CanvasGradient;

//import java.awt.*;
//import java.util.StringTokenizer;

class ResistorElm extends CircuitElm {
	double resistance;
	public ResistorElm(int xx, int yy) { super(xx, yy); resistance = 100; }
	public ResistorElm(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
		super(xa, ya, xb, yb, f);
		resistance = new Double(st.nextToken()).doubleValue();
	}
	int getDumpType() { return 'r'; }
	String dump() {
		return super.dump() + " " + resistance;
	}

	Point ps3, ps4;
	void setPoints() {
		super.setPoints();
		calcLeads(32);
		ps3 = new Point();
		ps4 = new Point();
	}

	void draw(Graphics g) {
		int hs=6;
		double v1 = volts[0];
		double v2 = volts[1];
		setBbox(point1, point2, hs);
		//draw2Leads(g);
		g.context.save();
		g.context.setLineWidth(3.0);

		g.context.beginPath();
		//setVoltageColor(g, volts[0]);
		g.context.moveTo(point1.x, point1.y);
		g.context.lineTo(lead1.x,  lead1.y);
		
		setPowerColor(g, true);
		double len = Point.distance(lead1, lead2);
		g.context.transform(((double)(lead2.x-lead1.x))/len, ((double)(lead2.y-lead1.y))/len, -((double)(lead2.y-lead1.y))/len,((double)(lead2.x-lead1.x))/len,lead1.x,lead1.y);
		if (!sim.euroResistorCheckItem.getState()) {
			// draw zigzag
			for (int i=0; i<4; i++){
				g.context.lineTo((1+4*i)*len/16, hs);
				g.context.lineTo((3+4*i)*len/16, -hs);
			}
			g.context.lineTo(len, 0);

		} else    {
			g.context.strokeRect(0, -hs, len, 2.0*hs);
		}
		g.context.restore();
		//setVoltageColor(g, volts[1]);
		CanvasGradient grad = g.context.createLinearGradient(lead1.x, lead1.y, lead2.x, lead2.y);
		grad.addColorStop(0, getVoltageColor(g,v1).getHexValue());
		grad.addColorStop(1.0, getVoltageColor(g,v2).getHexValue());
		g.context.setStrokeStyle(grad);
		g.context.lineTo(point2.x,  point2.y);
		g.context.stroke();
		
		if (sim.showValuesCheckItem.getState()) {
			String s = getShortUnitText(resistance, "\u03a9");
			drawValues(g, s, hs + 2);
		}
		doDots(g);
		drawPosts(g);
	}

	void calculateCurrent() {
		current = (volts[0]-volts[1])/resistance;
		//System.out.print(this + " res current set to " + current + "\n");
	}
	void stamp() {
		sim.stampResistor(nodes[0], nodes[1], resistance);
	}
	void getInfo(String arr[]) {
		arr[0] = "resistor";
		getBasicInfo(arr);
		arr[3] = "R = " + getUnitText(resistance, "\u03a9");
		arr[4] = "P = " + getUnitText(getPower(), "W");
	}
	public EditInfo getEditInfo(int n) {
		// ohmString doesn't work here on linux
		if (n == 0)
			return new EditInfo("Resistance (ohms)", resistance, 0, 0);
		return null;
	}
	public void setEditValue(int n, EditInfo ei) {
		if (ei.value > 0)
			resistance = ei.value;
	}
	int getShortcut() { return 'r'; }
}
