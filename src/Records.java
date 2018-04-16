import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Records{
	Object data;
	String dataType=null;
	int nByte;
	int serialCode;
	
	public Records(int serialCode) {
		this.serialCode = serialCode;
		this.nByte = getByte(serialCode);
		
	}
	
	public Records(String dataValue, String dataType) {
		this.dataType = dataType;
		if (dataValue.equalsIgnoreCase("")){
			this.data=0;
		}
		else {
			if (dataType.equalsIgnoreCase("tinyint")||dataType.equalsIgnoreCase("smallint")||dataType.equalsIgnoreCase("int")||dataType.equalsIgnoreCase("bigint")) {
				this.data = Integer.parseInt(dataValue);
			}
			else if (dataType.equalsIgnoreCase("double")||dataType.equalsIgnoreCase("real")) {
				this.data=Float.parseFloat(dataValue);
			}
			else if (dataType.equalsIgnoreCase("date")) {
				this.data = getDate(dataValue);
			}
			else if (dataType.equalsIgnoreCase("datetime")) {
				this.data = getDateTime(dataValue);
			} else if (dataType.equalsIgnoreCase("text")) {
				this.data = dataValue;
			}			
		}
		
		this.serialCode = getSerialCode(dataType, dataValue);
		if (dataType.equals("text")){
			nByte = dataValue.length();
			serialCode = serialCode+nByte;
		}
		this.nByte = getByte(serialCode);
		
		
	}
	
	//TODO: check with getDate method
	public String getDate(String dateString) {
		String pattern = "MM:DD:YYYY";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse(dateString);
            return Long.toString(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.toString(new Date().getTime());
		
	}
	
	//TODO: check with getDateTime method
	public String getDateTime(String dateTimeString) {
		String pattern = "MM:DD:YYYY hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse(dateTimeString);
            return Long.toString(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.toString(new Date().getTime());
	}
	
	public int getByte(int serialCode) {
		int nByte = 0;
		if (serialCode==0x00 || serialCode==0x04) {
			nByte=1;
    	}
    	else if (serialCode==0x01 || serialCode==0x05) {
    		 nByte=2;
    	}
    	else if (serialCode==0x02 || serialCode==0x06 || serialCode==0x08) {
    		 nByte=4;
    	}
    	else if (serialCode==0x03 || serialCode==0x07 || serialCode==0x09 || serialCode==0x0A || serialCode==0x0B) {
    		 nByte=8;
    	}
    	else
    	{
    		nByte = serialCode-0x0C;
    	}
        return nByte;
	}
	
	public int getSerialCode(String dataTypeName, String dataValue) {
		int serialCode=0xFF;
		if (dataTypeName.equalsIgnoreCase("tinyint") && dataValue.equals("")) {
			serialCode = 0x00;
		} else if (dataTypeName.equalsIgnoreCase("smallint") && dataValue.equals("")) {
			serialCode = 0x01;
		} else if ((dataTypeName.equalsIgnoreCase("int") || dataTypeName.equalsIgnoreCase("real")) && dataValue.equals("")) {
			serialCode = 0x02;
		} else if ((dataTypeName.equalsIgnoreCase("double") || dataTypeName.equalsIgnoreCase("bigint") || dataTypeName.equalsIgnoreCase("datetime") || dataTypeName.equalsIgnoreCase("date")) && dataValue.equals("")) {
			serialCode = 0x03;
		} else if (dataTypeName.equalsIgnoreCase("tinyint")) {
            serialCode = 0x04;
        }  else if (dataTypeName.equalsIgnoreCase("smallint")) {
            serialCode = 0x05;
        } else if (dataTypeName.equalsIgnoreCase("int")) {
            serialCode = 0x06;            
        } else if (dataTypeName.equalsIgnoreCase("bigint")) {
            serialCode = 0x07;
        } else if (dataTypeName.equalsIgnoreCase("real")) {
            serialCode = 0x08;
        } else if (dataTypeName.equalsIgnoreCase("double")) {
            serialCode = 0x09;
        } else if (dataTypeName.equalsIgnoreCase("datetime")) {
            serialCode = 0x0A;
        } else if (dataTypeName.equalsIgnoreCase("date")) {
            serialCode = 0x0B;
        } else if (dataTypeName.equalsIgnoreCase("text")) {
            serialCode = 0x0C;
        }
        return serialCode;
	}
}