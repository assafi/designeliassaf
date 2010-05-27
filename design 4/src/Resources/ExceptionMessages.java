package Resources;

/**
 * Class which purpose is to centralize managing all the error messages of the API
 * 
 */
public class ExceptionMessages {
	public static final String INVALID_PROPERTY_TYPE = "Invalid type for column. Allowed types are: Integer, String and Boolean only.";
	public static final String INVALID_OBJECT_TYPE_FOR_COLUMN = "Invalid value type. Can't set requested value to the column of different type.";
	public static final String INVALID_COLUMN_OBJECT = "Column object can't be null.";
}
