package com.appon.miniframework.layout;

import java.io.ByteArrayInputStream;

import com.appon.miniframework.Container;
import com.appon.miniframework.Control;
import com.appon.miniframework.Layout;
import com.appon.miniframework.MenuSerilize;
import com.appon.miniframework.Util;

//#ifdef DEKSTOP_TOOL 
//# import java.io.ByteArrayOutputStream;
//#endif

public class GridLayout implements Layout {

    public static final int LEFT_ALLIGN = 0;
    public static final int CENTER_ALLIGN = 1;
    public static final int RIGHT_ALLIGN = 2;
    public static final int TOP_ALLIGN = 0;
    public static final int BOTTOM_ALLIGN = 2;
    /** Control is not Stretch */
    public static final byte CELL_NONE_STRETCH = 0x0;
    /** Control is Stretch horizontally */
    public static final byte CELL_HORIZONTAL_STRETCH = 0x1;
    /** Control is Stretch vertically */
    public static final byte CELL_VERTICAL_STRETCH = 0x2;
    /** Control is Stretch horizontally and vertically */
    public static final byte CELL_HOR_VER_STRETCH = 0x3;
    /** Number of columns */
    private int _iNumColumn = 1;
    /** Number of rows */
    private int _iNumRow = 1;
    /** Specify which cell is to keep empty.False for keeping the cell empty */
    private boolean _bNullCellInfo[];
    /** Specify alignment of control in each cell.
     * It can be LAYOUT_LEFT_ALIGN or LAYOUT_RIGHT_ALIGN */
    private int _iCellAlignInfo[][];
    /** Specify how control should be get Stretched inside cell.
     * Use one of these CELL_NONE_STRETCH, CELL_HORIZONTAL_STRETCH,
     * CELL_VERTICAL_STRETCH, CELL_HOR_VER_STRETCH. */
    private int _iCellStretchArry[];
    private int _iCellX[];
    private int _iCellY[];
    private int _iCellWidth[];
    private int _iCellHeight[];
    private static final int UNIT_CELL_SIZE = 1;
    private int mergeCells[][];
    public GridLayout() {
       
      
    }

    public void initGrid(int numOfColumn, int numOfRow )
    {
          this._iNumColumn = Math.abs(numOfColumn);
        this._iNumRow = Math.abs(numOfRow);
        if (_iNumColumn == 0 || _iNumRow == 0) {
            return;
        }
        initializeGrids();
    }

    public int[][] getMergeCells() {
        return mergeCells;
    }

    public int[] getCellStretchArry() {
        return _iCellStretchArry;
    }

    public boolean[] getNullCellInfo() {
        return _bNullCellInfo;
    }

    public int[][] getCellAlignInfo() {
        return _iCellAlignInfo;
    }
   

    /**
     * Gets number of columns.
     * @return Number of columns
     */
    public int getColumns() {
        return _iNumColumn;
    }

    /**
     * Gets number of rows.
     * @return Number of rows.
     */
    public int getRows() {
        return _iNumRow;
    }

    /**
     * Sets which cell is to keep empty. False for keeping the cell empty.
     * @param gridID is id of the cell from where null cell information is about to overwrite.
     * @param nullCellInfo false to keep cell empty and true to fill it with control
     */
    public void setNullCellInfo(boolean[] nullCellInfo) {
        this._bNullCellInfo = nullCellInfo;
    }

    /**
     * Sets alignment of control in cell.
     * Like: Graphics.LEFT for left alignment, Graphics.RIGHT for right alignment etc.
     * @param gridID is id of the cell from where cell alignment information is about to overwrite.
     * @param info alignment of the controls in the cell.
     */
    public void setAllignCellInfo(int[][] cellAlignInfo) {
        this._iCellAlignInfo = cellAlignInfo;
       
    }

    private boolean checkForCorrectCellId(int cellID) {
        int totalCell = _iCellX.length;

        if (cellID < 0 || cellID > totalCell) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Specify how control should be get Stretched inside cell.
     * CELL_NONE_STRETCH: User can pass for default size.
     * CELL_HORIZONTAL_STRETCH : For Horizontal stretching.
     * CELL_VERTICAL_STRETCH: For Vertical stretching.
     * CELL_HOR_VER_STRETCH: For Vertical & Horizontal combined stretching.
     * @param gridID id of the cell from where Stretching information is about to overwrite.
     * @param info Stretching type.
     */
    public void setStretchCellInfo(int[] cellStretchArry) {
        _iCellStretchArry = cellStretchArry;
    }

    /**
     * Resets the all the cells merge information and the properties of cells
     * (nullCellInfo, cellAlignInfo & cellStretchInfo).
     */
    public void resetGrid() {

        initializeGrids();
        _iCellAlignInfo = null;
        _bNullCellInfo = null;
        _iCellStretchArry = null;
    }
    public void mergeCells(int[][] cells) {
        this.mergeCells = cells;
        if(cells == null)
            return;
        for (int i = 0; i < cells.length; i++) {
            mergeCells(cells[i]);
        }
    }
    
    /**
     * Merge the valid cells of grid. If given ids are not valid neighbors grid id's to merge then
     * Method will not merge any cell and will return false, On Success will return true.
     *
     * Valid Neighbor: Valid neighbor of cell will be UP, DOWN, RIGHT and LEFT cells, Diagonal
     * Cells will not be considered as valid neighbor.
     *
     * In Valid Neighbor: All cells which are not valid will be considered as in valid neighbors.
     *
     * After merging all the cells given as argument it will generate one merged cell,
     * Id of this cell will be the id of the smallest id specified in arry.
     * Other attribute like nullCellInfo, cellStretchInfo & cellAllignInfo will be assigned of
     * The smallest id specified in array.
     * After merging cells it will remove the merged cells Attribute information and will shift the
     * required cells ids up (Assigned attributes will remain same).
     *
     *  @param cells: Contains the id's of the cells which we want to merge.
     */
    public boolean mergeCells(int[] cells) {
        int[] x_backup = new int[_iCellX.length];
        int[] y_backup = new int[_iCellY.length];
        int[] width_backup = new int[_iCellWidth.length];
        int[] height_backup = new int[_iCellHeight.length];

        System.arraycopy(_iCellX, 0, x_backup, 0, _iCellX.length);
        System.arraycopy(_iCellY, 0, y_backup, 0, _iCellY.length);
        System.arraycopy(_iCellWidth, 0, width_backup, 0, _iCellWidth.length);
        System.arraycopy(_iCellHeight, 0, height_backup, 0, _iCellHeight.length);

        boolean tmpNullCellInfo[] = null;
        if (_bNullCellInfo != null) {
            tmpNullCellInfo = new boolean[_bNullCellInfo.length];
            System.arraycopy(_bNullCellInfo, 0, tmpNullCellInfo, 0, _bNullCellInfo.length);
        }
        int tmpCellAllignInfo[] = null;
        if (_iCellAlignInfo != null) {
            tmpCellAllignInfo = new int[_iCellAlignInfo.length];
            System.arraycopy(_iCellAlignInfo, 0, tmpCellAllignInfo, 0, _iCellAlignInfo.length);
        }
        int tmpCellStretchInfo[] = null;
        if (_iCellStretchArry != null) {
            tmpCellStretchInfo = new int[_iCellStretchArry.length];
            System.arraycopy(_iCellStretchArry, 0, tmpCellStretchInfo, 0, _iCellStretchArry.length);
        }

        
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if (mergeCell(cells[i], cells[j])) {
                    int delIndex = i;
                    if (cells[j] > cells[i]) {
                        delIndex = j;
                    }
                    int maxVal = cells[delIndex];
                    cells = deleteIteam(cells, delIndex);
                    for (int k = 0; k < cells.length; k++) {
                        if (maxVal < cells[k]) {
                            cells[k]--;
                        }
                    }
                    i = 0;
                    break;
                }
            }
        }
        if (cells.length != 1) {
            _iCellX = new int[x_backup.length];
            _iCellY = new int[x_backup.length];
            _iCellWidth = new int[x_backup.length];
            _iCellHeight = new int[x_backup.length];

            System.arraycopy(x_backup, 0, _iCellX, 0, x_backup.length);
            System.arraycopy(y_backup, 0, _iCellY, 0, x_backup.length);
            System.arraycopy(width_backup, 0, _iCellWidth, 0, x_backup.length);
            System.arraycopy(height_backup, 0, _iCellHeight, 0, x_backup.length);

            return false;
        }
        return true;
    }

    /**
     * Merge the valid cells of grid. If given ids are not valid neighbors grid id's to merge then
     * Method will not merge any cell and will return false, On Success will return true.
     *
     * Valid Neighbor: Valid neighbor of cell will be UP, DOWN, RIGHT and LEFT cells, Diagonal
     * Cells will not be considered as valid neighbor.
     *
     * In Valid Neighbor: All cells which are not valid will be considered as in valid neighbors.
     *
     * After merging all the cells given as argument it will generate one merged cell,
     * Id of this cell will be the id of the smallest id specified in argument of method.
     * Other attribute like nullCellInfo, cellStretchInfo & cellAllignInfo will be assigned of
     * The smallest id specified in argument of method.
     * After merging cells it will remove the merged cells Attribute information and will shift the
     * required cells ids up (Assigned attributes will remain same).
     *
     *  @param cellId1 the id of the first cell which we want to merge.
     *  @param cellId1 the id of the second cell which we want to merge.
     */
    public boolean mergeCell(int cellId1, int cellId2) {
        boolean canMerge = false;

        int tmp = cellId1;
        cellId1 = Math.min(cellId1, cellId2);
        cellId2 = Math.max(tmp, cellId2);

        if (cellId1 == cellId2 || cellId1 >= _iCellX.length || cellId2 >= _iCellX.length) {
            return false;
        }
        if ((_iCellX[cellId1] + _iCellWidth[cellId1] == _iCellX[cellId2] && _iCellY[cellId1] == _iCellY[cellId2] && _iCellY[cellId1] + _iCellHeight[cellId1] == _iCellY[cellId2] + _iCellHeight[cellId2])) {
            canMerge = true;
        } else if ((_iCellY[cellId1] + _iCellHeight[cellId1] == _iCellY[cellId2] && _iCellX[cellId1] == _iCellX[cellId2] && _iCellX[cellId1] + _iCellWidth[cellId1] == _iCellX[cellId2] + _iCellWidth[cellId2])) {
            canMerge = true;
        }

        if (canMerge == true) {
            int delIndex = Math.max(cellId1, cellId2);
            int newIndex = Math.min(cellId1, cellId2);

            if (_iCellX[cellId1] + _iCellWidth[cellId1] == _iCellX[cellId2] || _iCellX[cellId2] + _iCellWidth[cellId2] == _iCellX[cellId1]) {
                _iCellWidth[newIndex] = _iCellWidth[cellId1] + _iCellWidth[cellId2];
            }
            if (_iCellY[cellId1] + _iCellHeight[cellId1] == _iCellY[cellId2] || _iCellY[cellId2] + _iCellHeight[cellId2] == _iCellY[cellId1]) {
                _iCellHeight[newIndex] = _iCellHeight[cellId1] + _iCellHeight[cellId2];
            }

            _iCellX = deleteIteam(_iCellX, delIndex);
            _iCellY = deleteIteam(_iCellY, delIndex);
            _iCellWidth = deleteIteam(_iCellWidth, delIndex);
            _iCellHeight = deleteIteam(_iCellHeight, delIndex);

           
            if (_iCellStretchArry != null && _iCellStretchArry.length > delIndex) {
                _iCellStretchArry = deleteIteam(_iCellStretchArry, delIndex);
            }
            if (_bNullCellInfo != null && _bNullCellInfo.length > delIndex) {
                _bNullCellInfo = deleteIteam(_bNullCellInfo, delIndex);
            }
          
        }

        return canMerge;
    }

    private int[] deleteIteam(int[] arry, int index) {
        int tmpArry[] = new int[arry.length - 1];

        System.arraycopy(arry, 0, tmpArry, 0, index);
        System.arraycopy(arry, index + 1, tmpArry, index, arry.length - index - 1);

        return tmpArry;
    }

    private boolean[] deleteIteam(boolean[] arry, int index) {
        boolean tmpArry[] = new boolean[arry.length - 1];

        System.arraycopy(arry, 0, tmpArry, 0, index);
        System.arraycopy(arry, index + 1, tmpArry, index, arry.length - index - 1);

        return tmpArry;
    }

    private void initializeGrids() {
        int tmpTotalCell = (_iNumColumn * _iNumRow);

        if (_iNumColumn == 0 || _iNumRow == 0) {
            return;
        }
        _iCellX = new int[tmpTotalCell];
        _iCellY = new int[tmpTotalCell];
        _iCellWidth = new int[tmpTotalCell];
        _iCellHeight = new int[tmpTotalCell];

        for (int i = 0; i < tmpTotalCell; i++) {
            _iCellX[i] = UNIT_CELL_SIZE * (i % _iNumColumn);
            _iCellY[i] = UNIT_CELL_SIZE * (i / _iNumColumn);
            _iCellWidth[i] = UNIT_CELL_SIZE;
            _iCellHeight[i] = UNIT_CELL_SIZE;
        }


    }
    public void applyLayout(Container parent,Control control)
    {
        if (_iNumColumn == 0 || _iNumRow == 0) {
            return;
        }
        Container sourceContainer = (Container)control;
        int sourceWidth = sourceContainer.getBoundWidth();
        int sourceHeight = sourceContainer.getBoundHeight();

        int tmpCellWidth = sourceWidth / _iNumColumn;
        int tmpCellHeight = sourceHeight / _iNumRow;

        int correctionX = 0;
        int correctionY = 0;
//        // Uncomment this code when you want vertically overlapping cells.
//        if (sourceWidth % _iNumColumn != 0) {
//            correctionX++;
//        }
//        if (sourceHeight % _iNumRow != 0) {
//            correctionY++;        //x and y coordinate
//        }
        int temp_x = 0;
        int temp_y = 0;
        //Spacing between the controls
        int tmpTotalCell = _iCellX.length;
        int tmpCellCnt = 0;

        for (int idx = 0; idx < sourceContainer.getSize(); idx++) {

             if(sourceContainer.getChild(idx).getRelativeLocation() != null)
                 continue;
             
            if (_bNullCellInfo != null && _bNullCellInfo.length > tmpCellCnt) {
                while (_bNullCellInfo.length > tmpCellCnt && !_bNullCellInfo[tmpCellCnt] && tmpCellCnt < tmpTotalCell) {
                    tmpCellCnt++;
                }
            }

            temp_x = tmpCellWidth * (_iCellX[tmpCellCnt] + _iCellWidth[tmpCellCnt]) - ((tmpCellWidth * _iCellWidth[tmpCellCnt]) >> 1);


            temp_y = tmpCellHeight * (_iCellY[tmpCellCnt] + _iCellHeight[tmpCellCnt]) - ((_iCellHeight[tmpCellCnt] * tmpCellHeight) >> 1);

            if (_iCellAlignInfo != null && _iCellAlignInfo.length > tmpCellCnt) {
                
                if (_iCellAlignInfo[tmpCellCnt][0] == RIGHT_ALLIGN) {
                    temp_x += (((tmpCellWidth * _iCellWidth[tmpCellCnt]) >> 1) - ((sourceContainer.getChild(idx)).getWidth() >> 1));
                }
                if (_iCellAlignInfo[tmpCellCnt][0] == LEFT_ALLIGN) {
                    temp_x -= (((tmpCellWidth * _iCellWidth[tmpCellCnt]) >> 1) - ((sourceContainer.getChild(idx)).getWidth() >> 1));
                }
                if (_iCellAlignInfo[tmpCellCnt][1] == TOP_ALLIGN) {
                    temp_y -= (((tmpCellHeight * _iCellHeight[tmpCellCnt]) >> 1) - ((sourceContainer.getChild(idx)).getHeight() >> 1));
                }
                if (_iCellAlignInfo[tmpCellCnt][1] == BOTTOM_ALLIGN) {
                    temp_y += (((tmpCellHeight * _iCellHeight[tmpCellCnt]) >> 1) - ((sourceContainer.getChild(idx)).getHeight() >> 1));
                }
            }
            if (_iCellStretchArry != null && _iCellStretchArry.length > tmpCellCnt) {
                if ((_iCellStretchArry[tmpCellCnt] == CELL_HORIZONTAL_STRETCH)) {
                    (sourceContainer.getChild(idx)).setWidth(tmpCellWidth * _iCellWidth[tmpCellCnt] + correctionX);


                    temp_x = tmpCellWidth * (_iCellX[tmpCellCnt] + _iCellWidth[tmpCellCnt]) - ((tmpCellWidth * _iCellWidth[tmpCellCnt]) >> 1);

                }
                if ((_iCellStretchArry[tmpCellCnt] == CELL_VERTICAL_STRETCH)) {
                    (sourceContainer.getChild(idx)).setHeight(tmpCellHeight * _iCellHeight[tmpCellCnt] + correctionY);
                    temp_y = tmpCellHeight * (_iCellY[tmpCellCnt] + _iCellHeight[tmpCellCnt]) - ((_iCellHeight[tmpCellCnt] * tmpCellHeight) >> 1);
                }
                if ((_iCellStretchArry[tmpCellCnt] == CELL_HOR_VER_STRETCH)) {
                    (sourceContainer.getChild(idx)).setWidth(tmpCellWidth * _iCellWidth[tmpCellCnt] + correctionX);
                    (sourceContainer.getChild(idx)).setHeight(tmpCellHeight * _iCellHeight[tmpCellCnt] + correctionY);

                    temp_x = tmpCellWidth * (_iCellX[tmpCellCnt] + _iCellWidth[tmpCellCnt]) - ((tmpCellWidth * _iCellWidth[tmpCellCnt]) >> 1);
                    temp_y = tmpCellHeight * (_iCellY[tmpCellCnt] + _iCellHeight[tmpCellCnt]) - ((_iCellHeight[tmpCellCnt] * tmpCellHeight) >> 1);
                }

            }
            
            Control base = (sourceContainer.getChild(idx));
            base.setX(correctionX + temp_x - (base.getWidth() >> 1));
            base.setY(temp_y + correctionY - (base.getHeight() >> 1));
            if (tmpCellCnt < tmpTotalCell - 1) {
                tmpCellCnt++;
            }

        }
    }
   
    public void cleanUp()
    {
        _bNullCellInfo = null;
        _iCellStretchArry = _iCellX = _iCellY = _iCellWidth = _iCellHeight = null;
    }

   
    public byte[] serialize() throws Exception {
         //#ifdef DEKSTOP_TOOL
         //# ByteArrayOutputStream bos = new ByteArrayOutputStream();
         //# Util.writeInt(bos,getColumns(),1);
         //# Util.writeInt(bos,getRows(),1);
         //# Util.write2DIntArray(bos, getMergeCells());
         //# Util.writeIntArray(bos, getCellStretchArry());
         //# Util.writeBooleanArray(bos, getNullCellInfo());
         //# Util.write2DIntArray(bos, getCellAlignInfo());
         //# bos.flush();
         //# byte data[] = bos.toByteArray();
         //# bos.close();
         //# bos = null;
         //# return data;
         //#else
	return null;
        //#endif
    }

   
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
         
         initGrid(Util.readInt(bis,1),Util.readInt(bis,1));
         mergeCells(Util.read2DIntArray(bis));
         setStretchCellInfo(Util.readIntArray(bis));
         setNullCellInfo( Util.readBooleanArray(bis));
         setAllignCellInfo(Util.read2DIntArray(bis));
         bis.close();
         return null;
    }


    public int getClassCode() {
        return MenuSerilize.LAYOUT_GRID_TYPE;
    }

  
    public void port() {
    
    }
   
    private int getMaxWidth(Container parent)
    {
        int h = 0;
        for (int i = 0; i < parent.getSize(); i++) {
            if(parent.getChild(i).getWidth() > h && !parent.getChild(i).isSkipParentWrapSizeCalc())
                h = parent.getChild(i).getWidth();
        }
        return h;
    }
   private int getMaxHeight(Container parent)
    {
        int h = 0;
        for (int i = 0; i < parent.getSize(); i++) {
            if(parent.getChild(i).getHeight() > h && !parent.getChild(i).isSkipParentWrapSizeCalc())
                h = parent.getChild(i).getHeight();
        }
        return h;
    }
  
    public int getPreferedWidth(Container parent) {
       return _iNumColumn * getMaxWidth(parent);
    }

   
    public int getPreferedHeight(Container parent) {
        return _iNumRow * getMaxHeight(parent);
    }
    
}
