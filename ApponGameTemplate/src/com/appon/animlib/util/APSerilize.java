/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib.util;

//#ifdef DEKSTOP_TOOL
//# import com.appon.tool.ui.util.FlexiArrayItem;
//# import com.appon.tool.ui.util.FlexiArray;
//# import com.appon.animlibeditor.APAnimation;
//# import com.appon.animlibeditor.APAnimationGroup;
//# import com.appon.animlibeditor.APLayer;
//# import com.appon.animlibeditor.APTimeFrame;
//# import com.appon.tool.ui.models.LoadedImageModel;
//# import com.appon.tool.ui.models.Module;
//# import com.appon.tool.ui.models.ModuleEditorModel;
//# import com.appon.tool.ui.models.PaletteModel;
//#endif
import com.appon.animlib.ENAnimation;
import com.appon.animlib.ENAnimationGroup;
import com.appon.animlib.ENLayer;
import com.appon.animlib.ENTimeFrame;
import com.appon.animlib.basicelements.APArcShape;
import com.appon.animlib.basicelements.APLineShape;
import com.appon.animlib.basicelements.APModuleShape;
import com.appon.animlib.basicelements.APPolygonShape;
import com.appon.animlib.basicelements.APRectShape;
import com.appon.animlib.basicelements.APTraingleShape;

/**
 *
 * @author acer
 */
public class APSerilize extends Serilize {

	private static APSerilize instance;

	public static final int ENANIMATION_TYPE = 3000;
	public static final int ENANIMATION_GROUP_TYPE = ENANIMATION_TYPE + 1;
	public static final int ENLAYER_TYPE = ENANIMATION_GROUP_TYPE + 1;
	public static final int ENTIMEFRAME_TYPE = ENLAYER_TYPE + 1;
	public static final int SHAPE_TYPE_APARC = ENTIMEFRAME_TYPE + 1;
	public static final int SHAPE_TYPE_APMODULE = SHAPE_TYPE_APARC + 1;
	public static final int SHAPE_TYPE_APLINE = SHAPE_TYPE_APMODULE + 1;
	public static final int SHAPE_TYPE_APPOLYGON = SHAPE_TYPE_APLINE + 1;
	public static final int SHAPE_TYPE_APTRAINGLE = SHAPE_TYPE_APPOLYGON + 1;
	public static final int SHAPE_TYPE_APRECT = SHAPE_TYPE_APTRAINGLE + 1;

	// #ifdef DEKSTOP_TOOL
	// # public static final int ANIMATION_GROUP_TYPE = SHAPE_TYPE_APRECT + 1;
	// # public static final int ANIMATION_TYPE = ANIMATION_GROUP_TYPE + 1;
	// # public static final int APLAYER_TYPE = ANIMATION_TYPE + 1;
	// # public static final int APTIME_FRAME_TYPE = APLAYER_TYPE + 1;
	// # public static final int FLEXI_ARRAY_TYPE = APTIME_FRAME_TYPE + 1;
	// # public static final int FLEXI_ARRAY_ITEM_TYPE = FLEXI_ARRAY_TYPE + 1;
	// # public static final int MODULE_TYPE = FLEXI_ARRAY_ITEM_TYPE + 1;
	// # public static final int PALLETS_TYPE = MODULE_TYPE + 1;
	// # public static final int MODULE_EDITOR_MODEL = PALLETS_TYPE + 1;
	// # public static final int LOADED_IMAGE_TYPE = MODULE_EDITOR_MODEL + 1;
	// #endif

	private APSerilize() {
	}

	public static APSerilize getInstance() {
		if (instance == null) {
			instance = new APSerilize();
		}
		return instance;
	}

	public Serilizable getClassObject(int classcode, int id, int additionalData) {
		switch (classcode) {
			case ENANIMATION_TYPE :
				return new ENAnimation();
			case ENANIMATION_GROUP_TYPE :
				return new ENAnimationGroup();
			case ENLAYER_TYPE :
				return new ENLayer();
			case ENTIMEFRAME_TYPE :
				return new ENTimeFrame();
			case SHAPE_TYPE_APARC :
				return new APArcShape(-1);
			case SHAPE_TYPE_APMODULE :
				return new APModuleShape(-1);
			case SHAPE_TYPE_APLINE :
				return new APLineShape(-1);
			case SHAPE_TYPE_APPOLYGON :
				return new APPolygonShape(-1);
			case SHAPE_TYPE_APTRAINGLE :
				return new APTraingleShape(-1);
			case SHAPE_TYPE_APRECT :
				return new APRectShape(-1);

				// #ifdef DEKSTOP_TOOL
				// # case ANIMATION_GROUP_TYPE:
				// # return new APAnimationGroup();
				// # case ANIMATION_TYPE:
				// # return new APAnimation();
				// # case APLAYER_TYPE :
				// # return new APLayer(null);
				// # case APTIME_FRAME_TYPE:
				// # return new APTimeFrame();
				// # case FLEXI_ARRAY_TYPE :
				// # return new FlexiArray();
				// # case FLEXI_ARRAY_ITEM_TYPE :
				// # return new FlexiArrayItem(0);
				// #
				// # case MODULE_TYPE :
				// # return new Module(null);
				// #
				// # case PALLETS_TYPE :
				// # return new PaletteModel();
				// #
				// # case LOADED_IMAGE_TYPE :
				// # return new LoadedImageModel();
				// #
				// # case MODULE_EDITOR_MODEL :
				// # return ModuleEditorModel.getInstance();
				// #endif

		}

		return null;
	}

}
