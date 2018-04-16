import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtils {
	static long pageSize = 512; 

    public boolean deleteRecursive(File path) throws FileNotFoundException{
        if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        boolean successFlag = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
            	successFlag = successFlag && deleteRecursive(f);
            }
        }
        return successFlag && path.delete();
    }
    
	public long getPagePointer(RandomAccessFile file){
		int locInPage = 4;
		long pagePointer = 0;
		int nPage=0;
		try {
			file.seek(locInPage);
			pagePointer = file.readInt();
			while (pagePointer!=-1) {
				nPage+=1;
				long currentLoc = nPage*pageSize+locInPage;
				file.seek(currentLoc);
				pagePointer = file.readInt();
			}
		}
		catch (IOException e) {	
			e.printStackTrace();
		}
		return nPage*pageSize;
	}
	
	
	public int getRow_id(RandomAccessFile file) {
		int pointerLocInPage = 4;
		int nRecordLocInPage = 1;
		long pagePointer = 0;
		int nPage=0;
		int totalRecord = 0;
		int pageRecord = 0;
		try {
			file.seek(pointerLocInPage);
			pagePointer = file.readInt();
			
			file.seek(nRecordLocInPage);
			pageRecord = file.readByte();
			totalRecord+=pageRecord;
			while (pagePointer!=-1) {
				nPage+=1;
				long currentPointerLoc = nPage*pageSize+pointerLocInPage;
				System.out.println("CurrentPointerLoc: "+currentPointerLoc);
				file.seek(currentPointerLoc);
				pagePointer = file.readInt();
				System.out.println("PagePointer: "+pagePointer);
				long currentRecordLoc = nPage*pageSize + nRecordLocInPage;
				System.out.println("currentRecordLoc:"+currentRecordLoc);
				file.seek(currentRecordLoc);
				pageRecord = file.readByte();
				System.out.println("pageRecord:"+pageRecord);
					
				totalRecord+=pageRecord;
			}
		}
		catch (IOException e) {	
			e.printStackTrace();
		}
		return totalRecord+1;
	}
	

	/**
	 *  Stub method to check the datatype of the string and return appropriate serial code
	 *  @param file is a RandomAccessFile of the user input
	 */
	public int getnCellInFile(RandomAccessFile file, long pagePointer) {
		int nCell = -1;
		try {
			file.seek(pagePointer+1);
			nCell = file.readByte();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return nCell;
	}
	
	public void setnCellInFile(RandomAccessFile file, long pagePointer, int currentCell) {
		try {
			file.seek(pagePointer+1);
			int updatedCurrentCell = currentCell+1;
			file.writeByte(updatedCurrentCell);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void setStartOfContent(RandomAccessFile file, long pagePointer, int writeLoc) {
		try {
			file.seek(pagePointer+2);
			file.writeShort(writeLoc);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public int getoffset(RandomAccessFile file, long pagePointer) {
		int offset = -99;
		try {
			file.seek(pagePointer+ 2);
			offset = file.readShort();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return offset;
	}
	
	public long getIndexOffset(int nCell, long pagePointer) {
	
		/*skipping cell to write indexOffset:
		 * First 8 bytes for page offset and nCell and all
		 * From 8th byte we are starting to write index
		 * Now the location of our new index: 8+nCell*2 
		 */
		long indexOffset = pagePointer+ 8+2*nCell;
		return indexOffset;
	}
	
	public void setIndexOffset(RandomAccessFile file, long indexWriteLoc, int writeLoc) {
		try {
			file.seek(indexWriteLoc);
			file.writeShort(writeLoc);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 *  Stub method to check whether table exist
	 *  @param showTableStr is a String of the user input
	 */
	public boolean tableExists(String filePath) {
		boolean existFlag = false;
		try {
			File f= new File(filePath);
			
			if (f.exists() && !f.isDirectory()) {
				existFlag=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return existFlag;
	}

	public boolean nextPageExist(RandomAccessFile file, long pagePointer) {
		boolean pageExistFlag = false;
		try {
			file.seek(pagePointer+4);
			int nextPagePointer = file.readInt();
			if (nextPagePointer!=-1) {
				pageExistFlag = true;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return pageExistFlag;
		
		
	}
    
    
}