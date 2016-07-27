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

import com.google.gwt.canvas.dom.client.Context2d;

public class Graphics {
	
	float zoom;
	
	Context2d context;
	int currentFontSize;
	Font currentFont= null;
	Color lastColor;
	
	  public Graphics(Context2d context, float zoom) {
		    this.context = context;
		    // this fixes the half-pixel alignment issue when drawing to an HTML canvas
		    this.setZoom(zoom);
		  }
	  
	  public void setZoom(float zoom) {
		  this.zoom = zoom;
		  this.context.setTransform(zoom, 0, 0, zoom, 0.5*zoom, 0.5*zoom);		  
	  }
	  
	  public void setColor(Color color) {
		    if (color != null) {
		      String colorString = color.getHexValue();
		      context.setStrokeStyle(colorString);
		      context.setFillStyle(colorString);
		    } else {
		      System.out.println("Ignoring null-Color");
		    }
		    lastColor=color;
		  }
	  
	  public void setColor(String color) {
	      context.setStrokeStyle(color);
	      context.setFillStyle(color);
	      lastColor=null;
	  }
	  
	  public void fillRect(double x, double y, double width, double height) {
		//  context.beginPath();
		  context.fillRect(x, y, width, height);
		//  context.closePath();
	  }
	  
	  public void drawRect(double x, double y, double width, double height) {
		//  context.beginPath();
		  context.strokeRect(x, y, width, height);
		//  context.closePath();
	  }
	  
	  public void fillOval(double x, double y, double width, double height) {
		  context.beginPath();
		  context.arc(x+width/2, y+width/2, width/2, 0, 2.0*3.14159);
		  context.closePath();
		  context.fill();
	  }
	  
	  public void drawString(String s, double x, double y){
		//  context.beginPath();
		  context.fillText(s, x, y);
		//  context.closePath();
	  }
	  
	  public void setLineWidth(double width){
		  context.setLineWidth(width);
	  }
	  
	  public void drawLine(double x1, double y1, double x2, double y2) {
		  context.beginPath();
		  context.moveTo(x1, y1);
		  context.lineTo(x2, y2);
		  context.stroke();
	  }
	  
	  public void drawPolyline(int[] xpoints, int[] ypoints, int n) {
		  int i;
		  context.beginPath();
		  for (i=0; i<n;i++){
			  if (i==0)
				  context.moveTo(xpoints[i],ypoints[i]);
			  else
				  context.lineTo(xpoints[i],ypoints[i]);
		  }
		  context.closePath();
		  context.stroke();
	  }
	
	  
	  public void fillPolygon(Polygon p) {
		  int i;
		  context.beginPath();
		  for (i=0; i<p.npoints;i++){
			  if (i==0)
				  context.moveTo(p.xpoints[i],p.ypoints[i]);
			  else
				  context.lineTo(p.xpoints[i],p.ypoints[i]);
		  }
		  context.closePath();
		  context.fill();
		  }
	  
	  public void setFont(Font f){
		  if (f!=null){
			  context.setFont(f.fontname);
			  currentFontSize=f.size;
			  currentFont=f;
		  }
	  }
	  
	  Font getFont(){
		  return currentFont;
	  }
	  
}