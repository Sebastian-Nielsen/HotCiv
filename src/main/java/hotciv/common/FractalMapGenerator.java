package hotciv.common;

import thirdparty.ThirdPartyFractalGenerator;

public class FractalMapGenerator {

	public static String[] makeFractalLandscape() {
		ThirdPartyFractalGenerator generator =
	      new ThirdPartyFractalGenerator();

		String[] layout = new String[16];
		String line;
	    for ( int r = 0; r < 16; r++ ) {
	      line = "";
	      for ( int c = 0; c < 16; c++ ) {
	        line += generator.getLandscapeAt(r,c);
	      }
	      layout[r] = line;
	    }
	    return layout;
	}

}
