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

public class Point {
	public double x;
	public double y;

	public Point(int i, int j) {
		x=(double)i;
		y=(double)j;
	}
	public Point(double i, double j) {
		x=i;
		y=j;
	}

	public Point(Point p) {
		x=p.x;
		y=p.y;
	}

	public Point() {
		x=0;
		y=0;
	}

	public void setLocation(Point p) {
		x=p.x;
		y=p.y;
	}
	
	public static double distance(Point p1, Point p2) {
		double x = p1.x-p2.x;
		double y = p1.y-p2.y;
		return Math.sqrt(x*x+y*y);
	}
	
	public static Point interpolate(Point p1, Point p2, double ratio) {
		return new Point(p1.x + (p2.x-p1.x)*ratio, p1.y + (p2.y-p1.y)*ratio);
	}
	
	public static Point shiftSideways(Point toShift, Point endpoint1, Point endpoint2, double shiftDistance) {
		double gx = endpoint2.y-endpoint1.y;
		double gy = endpoint1.x-endpoint2.x;
		shiftDistance /= Math.sqrt(gx*gx+gy*gy);
		return new Point(toShift.x + shiftDistance*gx, toShift.y + shiftDistance*gy);
	}
	
	public static Point[] getTwoOppositelyShiftedPoints(Point toShift, Point endpoint1, Point endpoint2, double shiftDistance) {
		Point r[] = new Point[2];
		r[0] = shiftSideways(toShift, endpoint1, endpoint2,  shiftDistance);
		r[1] = shiftSideways(toShift, endpoint1, endpoint2, -shiftDistance);
		return r;
	}
	
	public static Point interpolateAndShift(Point p1, Point p2, double ratio, double shiftDistance) {
		double gx = p2.y-p1.y;
		double gy = p1.x-p2.x;
		shiftDistance /= Math.sqrt(gx*gx+gy*gy);
		Point c = interpolate(p1, p2, ratio);
		return shiftSideways(c, p1, p2, shiftDistance);
	}
}