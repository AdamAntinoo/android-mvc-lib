#if (${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

#if (${IMPORT_BLOCK} != "")${IMPORT_BLOCK}
import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
#end
#parse("File Header.java")

#if (${VISIBILITY} == "PUBLIC")public #end interface ${NAME} #if (${INTERFACES} != "")extends ${INTERFACES} #end {
	public String toString();
}
