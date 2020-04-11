#if (${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

#if (${IMPORT_BLOCK} != "")${IMPORT_BLOCK}
#end
#parse("File Header.java")
#if (${VISIBILITY} == "PUBLIC")public #end #if (${ABSTRACT} == "TRUE")abstract #end #if (${FINAL} == "TRUE")final #end class ${NAME} #if (${SUPERCLASS} != "")extends ${SUPERCLASS} #end #if (${INTERFACES} != "")implements ${INTERFACES} #end {
private ${NAME}(){}
// - B U I L D E R
public static class Builder {
private ${NAME} onConstruction;
public Builder (){
this.onConstruction = new ${NAME}();
}
public ${NAME} build(){
return this.onConstruction;
}
}
}
