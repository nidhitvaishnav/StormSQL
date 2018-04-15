import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Records{
	Object data;
	String dataType;
	int nByte;
	int serialCode;
	public Records(String dataValue, String dataType) {
		this.dataType = dataType;
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
		else {
			this.data=null;
		}
		this.nByte = getByte(dataType);
		this.serialCode = getSerialCode(dataType);
		
		if (dataType.equals("text")){
			nByte = dataValue.length();
			serialCode = serialCode+nByte;
		}
	}
	
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
	
	public int getByte(String dataTypeName) {
		int nByte = 0;
		if (dataTypeName.equalsIgnoreCase("tinyint") && data.equals("null")) {
			nByte=1;
		} else if (dataTypeName.equalsIgnoreCase("smallint") && data.equals("null")) {
			nByte=2;
		} else if ((dataTypeName.equalsIgnoreCase("int") || dataTypeName.equalsIgnoreCase("real")) && data.equals("null")) {
			nByte=4;
		} else if ((dataTypeName.equalsIgnoreCase("double") || dataTypeName.equalsIgnoreCase("bigint") || dataTypeName.equalsIgnoreCase("datetime") || dataTypeName.equalsIgnoreCase("date")) && data.equals("null")) {
			nByte = 8;
		} else if (dataTypeName.equalsIgnoreCase("tinyint")) {
			nByte=1;
        }  else if (dataTypeName.equalsIgnoreCase("smallint")) {
        	nByte=2;
        } else if (dataTypeName.equalsIgnoreCase("int")) {
        	nByte=4;            
        } else if (dataTypeName.equalsIgnoreCase("bigint")) {
        	nByte = 8;
        } else if (dataTypeName.equalsIgnoreCase("real")) {
        	nByte=4;
        } else if (dataTypeName.equalsIgnoreCase("double")) {
        	nByte = 8;
        } else if (dataTypeName.equalsIgnoreCase("datetime")) {
        	nByte = 8;
        } else if (dataTypeName.equalsIgnoreCase("date")) {
        	nByte = 8;
        } else if (dataTypeName.equalsIgnoreCase("text")) {
            nByte = -1;
        }
		
        return nByte;
	}
	
	public int getSerialCode(String dataTypeName) {
		int serialCode=0xFF;
		if (dataTypeName.equalsIgnoreCase("tinyint") && data.equals("null")) {
			serialCode = 0x00;
		} else if (dataTypeName.equalsIgnoreCase("smallint") && data.equals("null")) {
			serialCode = 0x01;
		} else if ((dataTypeName.equalsIgnoreCase("int") || dataTypeName.equalsIgnoreCase("real")) && data.equals("null")) {
			serialCode = 0x02;
		} else if ((dataTypeName.equalsIgnoreCase("double") || dataTypeName.equalsIgnoreCase("bigint") || dataTypeName.equalsIgnoreCase("datetime") || dataTypeName.equalsIgnoreCase("date")) && data.equals("null")) {
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