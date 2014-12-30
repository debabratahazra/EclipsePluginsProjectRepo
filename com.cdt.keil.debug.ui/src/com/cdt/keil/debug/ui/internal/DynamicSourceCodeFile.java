package com.cdt.keil.debug.ui.internal;

public class DynamicSourceCodeFile {

	public String getSourceFilePath(String startAddress, int type) {
		//type 0-> relative file path
        //     1-> absolute file path.
		
		String sFilename = getSourceFilename(startAddress);
		String[] srcFilename = SourceFileContentProvider.sourceFilePath();
		for(int i=0;i<srcFilename.length;i++){
			if(srcFilename[i].toLowerCase().contains(sFilename.trim().toLowerCase())){
				if(type==1){
					//Absolute file path
					return (srcFilename[i]);
				}else{
					//Relative file path
					String projectName = SourceFileContentProvider.getProjectName();
					int index = srcFilename[i].lastIndexOf(projectName);
					srcFilename[i] = srcFilename[i].substring(index, srcFilename[i].length());
					return srcFilename[i];			
				}
			}
		}
		return null;
	}

	private String getSourceFilename(String startAddress) {
		//Return Source filename w.r.t. start address.
		
		HighLevelStructure hls = new HighLevelStructure();
		for(int i=0;i<HighLevelStructure.totalLineCount;i++){
			if(hls.getSAddress(i).trim().equalsIgnoreCase(startAddress.trim())){
				return (hls.getFilename(i));
			}
		}		
		return null;
	}

}
