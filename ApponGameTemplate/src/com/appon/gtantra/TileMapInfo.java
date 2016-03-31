package com.appon.gtantra;



import java.io.InputStream;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author acer
 */
public class TileMapInfo {

    private int tileWidth;
    private int tileHeight;
    private int totalRows , totalColums;
//    private int cells[];
//    private int flags[];
    
    private int clipX , clipY, clipWidth,clipHeight;
    private GTantra mapTantra;
    private Vector layers = new Vector();
    private int markerTileIndex = -1;
    public TileMapInfo() {
       
    }

    public void setMarkerTileIndex(int markerTileIndex) {
        this.markerTileIndex = markerTileIndex;
    }

    public void setMapTantra(GTantra mapTantra) {
        this.mapTantra = mapTantra;
    }
    public Layer getLayer(int index)
    {
        return (Layer)layers.elementAt(index);
    }
    public Vector getLayers() {
        return layers;
    }
    public int getTileIndex(int col,int row,int layerId)
    {
       
        return getLayer(layerId).cells[row * totalColums + col];
    }
    public int getTileIndexFromPosition(int x,int y,int layerId)
    {
        int row = y / tileHeight;
        int col = x / tileWidth;
        return getLayer(layerId).cells[row * totalColums + col];
    }
    public int getCurrentRow(int y){
        return y / tileHeight;
    }
    public int getCurrentCol(int x){
        return x / tileWidth;
    }
    public void loadTitleMap(InputStream is,int mapIndex) 
    {
        try {
            
        
        int totalMaps = byteToint(is, 1);
        for (int i = 0; i < mapIndex; i++) {
            is.skip(4);
            int cols = byteToint(is, 2);
            int rows = byteToint(is, 2);
            int totalLayers = byteToint(is, 2);
            is.skip(cols * rows * totalLayers * 3);
        }
        tileWidth = byteToint(is, 2);
        tileHeight = byteToint(is, 2);
        totalColums = byteToint(is, 2);
        totalRows  = byteToint(is, 2);
        int totalLayers = byteToint(is, 2);
        layers.removeAllElements();
        for (int i = 0; i < totalLayers; i++) {
            Layer layer = new Layer();
            layer.cells = new int[totalColums * totalRows];
            layer.flags = new int[totalColums * totalRows];
          
            for(int j=0;j<(totalColums * totalRows);j++)
            {
                layer.cells[j] = byteToint(is, 2);
          
            }
            for(int j=0;j<(totalColums * totalRows);j++)
            {
                layer.flags[j] = byteToint(is, 1);
            }
            layers.addElement(layer);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int byteToint(InputStream is,int noofBytes) throws Exception
    {
        int _lib_pOffset = 0;
        switch (noofBytes)
        {
            case 1:
                _lib_pOffset = ((byte)is.read() & 0xFF);
                break;
            case 2:
                _lib_pOffset = ((byte)is.read() & 0xFF);
                _lib_pOffset += ((byte)is.read() & 0xFF) << 8;
                break;
            case 3:
                _lib_pOffset = ((byte)is.read() & 0xFF);
                _lib_pOffset += ((byte)is.read() & 0xFF) << 8;
                if((byte)is.read()==(byte)1)
                    _lib_pOffset=_lib_pOffset*(-1);

                break;
            case 4:
                _lib_pOffset = ((byte)is.read() & 0xFF);
                _lib_pOffset += ((byte)is.read() & 0xFF) << 8;
                _lib_pOffset += ((byte)is.read() & 0xFF) << 16;
                _lib_pOffset += ((byte)is.read()& 0xFF) << 24;
                break;
        }
      
        return _lib_pOffset;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
    
    public int getTotalColums() {
        return totalColums;
    }

    public int getTotalRows() {
        return totalRows;
    }
    ///////////////// map drawing/////////////
    public void drawMap(Canvas g,int drawX, int drawY,int width,int height, int x,int y,Paint paintObject)
    {
        
        int colNum = x / tileWidth;
        int rowNum = y / tileHeight;
        int endCol = (x + width) / tileWidth;
        int endRow = (y + height) / tileHeight;
        if((x + width) % tileWidth != 0)
            endCol++;
        if((y + height) % tileHeight != 0)
            endRow++;
        
        
//        int tileId = (rowNum * totalColums) + colNum;
        int shiftX = x - (colNum * tileWidth);
        int shiftY = y - (rowNum * tileHeight);
        saveNSetClip(g, drawX, drawY, width, height);
        for (int i = colNum; i < endCol; i++) {
            for (int j = rowNum; j < endRow; j++) {
                int tileId = (j * totalColums) + i;
                
                drawTile(g,tileId, drawX + ((i - colNum)*tileWidth) - shiftX, drawY + ((j - rowNum)*tileHeight) - shiftY,paintObject);
            }
        }
        restoreClip(g);
    }

    private void drawTile(Canvas g,int tileId,int x,int y,Paint paintObject)
    {
    
        for (int i = 0; i < layers.size(); i++) {
            Layer object = (Layer)layers.elementAt(i);
            if(object.isMarker())
                continue;
              
            if(tileId >= 0 && tileId <=  object.getCells().length - 1)
            {
                 if(markerTileIndex != -1 && object.getCells()[tileId] == markerTileIndex)
                {
                    return;
                }
                mapTantra.DrawModule(g, object.getCells()[tileId], x, y,object.getFlags()[tileId]);
            }else {
                paintObject.setColor(0);
                Rect r =new Rect(x, y, tileWidth, tileHeight);
                g.drawRect(r, paintObject);
                //g.fillRect(x, y, tileWidth, tileHeight);
              
            }
            
        }
    }
   
    private void saveNSetClip(Canvas g,int x,int y,int width,int height)
    {
    	Rect r=g.getClipBounds();
        clipX = r.left;
        clipY = r.top;
        clipWidth = r.width();
        clipHeight = r.height();
        g.clipRect(clipX,clipY,clipWidth,clipHeight);
//        g.setClip(x, y, width, height);
    }
    public void restoreClip(Canvas g)
    {
    	g.clipRect(clipX, clipY, clipWidth, clipHeight);
//        g.setClip(clipX, clipY, clipWidth, clipHeight);
    }
    
}
