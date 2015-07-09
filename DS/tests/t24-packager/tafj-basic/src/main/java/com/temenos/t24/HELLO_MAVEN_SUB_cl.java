/*   |   *//************** TAFJ INFO ***************************************/
/*   |   *//* <TAFJ-BP>W:\svn\DS\trunk\tests\t24-packager\tafj-basic\basic\HELLO.MAVEN.SUB.b<\TAFJ-BP>  */
/*   |   *//* <TAFJ-BPA>com.temenos.t24<\TAFJ-BPA>  */
/*   |   *//* <TAFJ-BN>HELLO.MAVEN.SUB<\TAFJ-BN>  */
/*   |   *//************** TAFJ INFO ***************************************/
/*   |   */package com.temenos.t24;
/*   |   */
/*   |   */import com.temenos.tafj.common.jVar;
/*   |   */import com.temenos.tafj.common.jVarFactory;
/*   |   */import com.temenos.tafj.common.jVarConstFactory;
/*   |   */import com.temenos.tafj.common.PreciseDecimal;
/*   |   */import com.temenos.tafj.common.jSession;
/*   |   */import com.temenos.tafj.common.Environment;
/*   |   */import com.temenos.tafj.common.BasicRuntimeException;
/*   |   */import com.temenos.tafj.common.exception.NeedRestartException;
/*   |   */import com.temenos.tafj.common.jPosition;
/*   |   */import com.temenos.tafj.common.Constants;
/*   |   */import com.temenos.tafj.runtime.jRunTime;
/*   |   */import java.lang.reflect.Field;
/*   |   */import com.temenos.tafj.common.jSystem;
/*   |   */import com.temenos.tafj.t24.*;
/*   |   */
/*   |   */ //SUBROUTINE HELLO_MAVEN_SUB_cl
/*   |   */public class HELLO_MAVEN_SUB_cl extends jRunTime {
/*   |   */    private static jRunTime __instance;
/*   |   */    
/*   |   */    private boolean _inMove = false;
/*   |   */    public HELLO_MAVEN_SUB_cl(){
/*   |   */        __instance = this;
/*   |   */    }
/*   |   */    
/*   |   */    public static jRunTime getInstance(){
/*   |   */        return __instance;
/*   |   */    }
/*   |   */    
/*   |   */    // Invoked by the jRebel Plugin
/*   |   */    public void restart(){
/*   |   */       super.setNeedRestart(true);
/*   |   */    }
/*   |   */    
/*   |   */    @Override
/*   |   */    public void keepMoving(){
/*   |   */    	this._inMove = true;
/*   |   */    	while(this._inMove){
/*   |   */    		move();
/*   |   */    	}
/*   |   */    }
/*   |   */    public void move(){
/*   |   */    	try {
/*   |   */    		Thread.sleep(200);
/*   |   */    	} catch (InterruptedException e) {
/*   |   */    	}
/*   |   */    }
/*   |   */    @Override
/*   |   */    public void stopMoving(){
/*   |   */    	this._inMove = false;
/*   |   */    }
/*   |   */    
/*   |   */    protected int main(){
_l(     3);        CRT(op_cat("HELLO MAVEN I HAVE BEEN COMPILED WITH TAFJ ",_param));
/*   |   */        return LABEL_STOP;                            //END
/*   |   */    }
/*   |   */    
/*   |   */    public jVar invoke(Object ... args) {
/*   |   */        while(true){
/*   |   */            try{
/*   |   */                return invoke(args[0]);
/*   |   */            }catch(NeedRestartException nrt){
/*   |   */                invokeRestart("HELLO_MAVEN_SUB_cl",false,  new jVar[]{(jVar)this._param});
/*   |   */                super.setNeedRestart(false);
/*   |   */                create();  // recreate all the variables.
/*   |   */            }
/*   |   */        }
/*   |   */    }
/*   |   */    
/*   |   */    
/*   |   */    
/*   |   */    boolean _isBreak        = false; //flag if a break append in a loop
/*   |   */    boolean _isContinue     = false; //flag if a continue append in a loop
/*   |   */    boolean _loop_          = true;  //need it for the LOOP statement
/*   |   */    boolean _NeedInitialise = true;  //To know whether we have to initialize the common and vars or not
/*   |   */    String[] _varList       = null; //List of the variables in this program
/*   |   */    String[] _paramList     = null; //List of the parameter of this program
/*   |   */    
/*   |   */    
/*   |   */    public static synchronized jRunTime INSTANCE(jSession session) {
/*   |   */        jRunTime prg = null;
/*   |   */        prg =session.getRuntimeCache("HELLO_MAVEN_SUB_cl");
/*   |   */        if (prg == null) {
/*   |   */            prg = new HELLO_MAVEN_SUB_cl();
/*   |   */            prg.init(session);
/*   |   */        }
/*   |   */        return prg;
/*   |   */    }
/*   |   */    
/*   |   */    public void stack(HELLO_MAVEN_SUB_cl prg){
/*   |   */        if (session.setRuntimeCache("HELLO_MAVEN_SUB_cl", prg)){
/*   |   */            //
/*   |   */            // No need to initialise the common and the vars.
/*   |   */            //
/*   |   */            this._NeedInitialise = false;
/*   |   */        }
/*   |   */    }
/*   |   */    
/*   |   */    public jVar invoke(Object _param) {
/*   |   */        this._param = toVarNoCache(_param);
/*   |   */        int nStoredPrecision = session.getPrecision();
/*   |   */        invokeStart("HELLO_MAVEN_SUB_cl",false,  new jVar[]{(jVar)this._param});
/*   |   */        int nRet = main();
/*   |   */        if (nRet > 0){
/*   |   */            CB(nRet);
/*   |   */        }else{
/*   |   */            check(nRet);
/*   |   */        }
/*   |   */        release();
/*   |   */        invokeStop("HELLO_MAVEN_SUB_cl",false,  new jVar[]{(jVar)this._param});
/*   |   */        session.setPrecision(nStoredPrecision);
/*   |   */        if (_Cached) _NeedInitialise = false;
/*   |   */        stack(this);
/*   |   */        return _Sys_RetRoutine;
/*   |   */    }
/*   |   */    
/*   |   */    // Invoked whenever the debugger needs to know the BASIC source file name.
/*   |   */    public String getBASICName(){
/*   |   */        return "HELLO.MAVEN.SUB";
/*   |   */    }
/*   |   */    
/*   |   */    // Used by jRuntime when invoking the CodeCoverage.
/*   |   */    public int getNbLines(){
/*   |   */        return 1;
/*   |   */    }
/*   |   */    
/*   |   */    // Static metod used by the CodeCoverage when initializing all the classes
/*   |   */    public static int getNbLinesStatic(){
/*   |   */        return 1;
/*   |   */    }
/*   |   */    
/*   |   */    // Invoked whenever the debugger needs to know what variables are in this program.
/*   |   */    public String[] getVarList(){
/*   |   */       if (_varList == null){
/*   |   */          //Initialize the list
/*   |   */          _varList = new String[1];
/*   |   */          _varList[0] = "param(jVar)";
/*   |   */       }
/*   |   */       return _varList;
/*   |   */    }
/*   |   */    
/*   |   */    // Invoked whenever the debugger needs to get a Var Contents.
/*   |   */    public String getVarValue(String sVarName){
/*   |   */       int nPos = sVarName.lastIndexOf("(");
/*   |   */       String sType = "jVar"; // default.
/*   |   */       if (nPos > 0){
/*   |   */           sType = sVarName.substring(nPos + 1, sVarName.length()-1);
/*   |   */           sVarName = sVarName.substring(0,nPos);
/*   |   */       }
/*   |   */       sVarName = jSystem.convertNameVar(sVarName);
/*   |   */       try {
/*   |   */           @SuppressWarnings("unchecked")
/*   |   */           Class c = this.getClass();
/*   |   */           Field fVar = null;
/*   |   */           try{
/*   |   */               fVar = c.getDeclaredField(sVarName);
/*   |   */           }catch(NoSuchFieldException e){
/*   |   */               // In case we have a version with def class
/*   |   */               c = this.getClass().getSuperclass();
/*   |   */               fVar = c.getDeclaredField(sVarName);
/*   |   */           }
/*   |   */       if (sType.equals("long")){
/*   |   */           long jv = (Long)fVar.get(this);
/*   |   */           return String.valueOf(jv); 
/*   |   */       }else if (sType.equals("String")){
/*   |   */           String jv = (String)fVar.get(this);
/*   |   */           return jv;           
/*   |   */       }else if (sType.equals("unknow")){
/*   |   */          try{
/*   |   */               jVar jv = (jVar)fVar.get(this);
/*   |   */               return jv.toExternalString();
/*   |   */          }catch(Exception e){
/*   |   */    	   }
/*   |   */          try{
/*   |   */               String jv = (String)fVar.get(this);
/*   |   */               return jv;   
/*   |   */    	   }catch(Exception e){
/*   |   */          }
/*   |   */    	   try{
/*   |   */    		  long jv = (Long)fVar.get(this);
/*   |   */             return String.valueOf(jv); 
/*   |   */    	   }catch(Exception e){
/*   |   */          }
/*   |   */          return "N/A";
/*   |   */       }else{
/*   |   */          jVar jv = (jVar)fVar.get(this);
/*   |   */          return jv.toExternalString();
/*   |   */       }
/*   |   */       } catch (Exception e) {
/*   |   */          return "N/A";
/*   |   */       }
/*   |   */    }
/*   |   */    
/*   |   */    
/*   |   */    // Invoked whenever the debugger needs to set a Var Contents.
/*   |   */    public String setVarValue(String sVarName, String sValue) {
/*   |   */       int nPos = sVarName.lastIndexOf("(");
/*   |   */       String sType = "jVar"; // default.
/*   |   */       if (nPos > 0){
/*   |   */           sType = sVarName.substring(nPos + 1, sVarName.length()-1);
/*   |   */           sVarName = sVarName.substring(0,nPos);
/*   |   */       }
/*   |   */    	sVarName = jSystem.convertNameVar(sVarName);
/*   |   */     try {
/*   |   */           @SuppressWarnings("unchecked")
/*   |   */           Class c = this.getClass();
/*   |   */           Field fVar = null;
/*   |   */           try{
/*   |   */               fVar = c.getDeclaredField(sVarName);
/*   |   */           }catch(NoSuchFieldException e){
/*   |   */               // In case we have a version with def class
/*   |   */               c = this.getClass().getSuperclass();
/*   |   */               fVar = c.getDeclaredField(sVarName);
/*   |   */           }
/*   |   */    		if (sType.equals("long")) {
/*   |   */    			fVar.setLong(this, Long.parseLong(sValue));
/*   |   */    			return sValue;
/*   |   */    		} else if (sType.equals("String")) {
/*   |   */    			fVar.set(this, sValue);
/*   |   */    			return sValue;
/*   |   */    		} else {
/*   |   */    			jVar jv = (jVar) fVar.get(this);
/*   |   */    			jv.set(sValue);
/*   |   */    			return sValue;
/*   |   */    		}
/*   |   */    	} catch (Exception e) {
/*   |   */    		return "! Failure !";
/*   |   */    	}
/*   |   */    }
/*   |   */    
/*   |   */    //Variables
/*   |   */    protected jVar _param;
/*   |   */    
/*   |   */    // init method.
/*   |   */    public void init(jSession _s_ ) {
/*   |   */        super.init(_s_);
/*   |   */        
/*   |   */        if (_NeedInitialise){
/*   |   */            create();
/*   |   */        }else{
/*   |   */            reset();
/*   |   */        }
/*   |   */    }
/*   |   */    
/*   |   */    
/*   |   */    // create method.
/*   |   */    public void create() {
/*   |   */    }
/*   |   */    
/*   |   */    // reset method.
/*   |   */    public void reset() {
/*   |   */    }
/*   |   */    
/*   |   */    public void CLEAR() {
/*   |   */        _file0001.CLEAR();
/*   |   */        _param.CLEAR();
/*   |   */    }
/*   |   */    
/*   |   */    public void release() {
/*   |   */        //UNMAT
/*   |   */        
/*   |   */        //RELEASE
/*   |   */    }
/*   |   */    
/*   |   */    //Gosub
/*   |   */    protected void GOSUB(int nLabel) {
/*   |   */        GOSUB(nLabel, true);
/*   |   */    }
/*   |   */    
/*   |   */    protected void GOSUB(int nLabel, boolean checkCallStack) {
/*   |   */        int nRet = LABEL_NULL;
/*   |   */        if (checkCallStack){
/*   |   */            try{
/*   |   */                checkCallStack("-gs:" + getLabelName(nLabel));
/*   |   */            }catch(Exception e){
/*   |   */                session.setStateSubroutine(STATE_EXIT);
/*   |   */                nLabel = LABEL_EXIT;
/*   |   */                nRet = LABEL_EXIT;
/*   |   */            }
/*   |   */        }
/*   |   */        switch(nLabel){
/*   |   */        case main:
/*   |   */            nRet = main();
/*   |   */            break;
/*   |   */        default:
/*   |   */        }
/*   |   */        check(nRet);
/*   |   */    }
/*   |   */    
/*   |   */    //CB
/*   |   */    protected void CB(int sNext) {
/*   |   */        GOSUB(sNext, false);
/*   |   */    }
/*   |   */    
/*   |   */    //Flags
/*   |   */    
/*   |   */    //Labels
/*   |   */    protected final static int LABEL_EXIT = -3;
/*   |   */    protected final static int LABEL_STOP = -2;
/*   |   */    protected final static int LABEL_NULL = -1;
/*   |   */    protected final static int main = 0;
/*   |   */    
/*   |   */    //Only for callstack comparison
/*   |   */    private String getLabelName(int nLabel){
/*   |   */        return "";
/*   |   */    }
/*   |   */    
/*   |   */    // Invoked whenever the unitTest Framework needs to know what variables are in this program.
/*   |   */    public String[] getParamList(){
/*   |   */       if (_paramList == null){
/*   |   */          //Initialize the list
/*   |   */          _paramList = new String[1];
/*   |   */          _paramList[0] = "param";
/*   |   */       }
/*   |   */       return _paramList;
/*   |   */    }
/*   |   */    public jVar[] getParams(){
/*   |   */        return new jVar[]{
/*   |   */    _param/*   |   */    
/*   |   */       };
/*   |   */    }
/*   |   */    
/*   |   */    //For Debugger and tShow
/*   |   */    public String getPathFileNameBasic(){
/*   |   */        return "W:\\svn\\DS\\trunk\\tests\\t24-packager\\tafj-basic\\basic\\HELLO.MAVEN.SUB.b";
/*   |   */    }
/*   |   */    
/*   |   */    //For tShow
/*   |   */    public String getCompileInfo() {
/*   |   */        return "1369846290919	29 May 2013 18:51:30	W8432	1";
/*   |   */    }
/*   |   */    
/*   |   */    //For tShow
/*   |   */    public String getPackageBasic() {
/*   |   */        return "com.temenos.t24";
/*   |   */    }
/*   |   */    
/*   |   */    //For tShow
/*   |   */    public String getImportBasic() {
/*   |   */        return "com.temenos.t24";
/*   |   */    }
/*   |   */    
/*   |   */    //For tShow
/*   |   */    public String getVersion() {
/*   |   */        return "DEV_201304.0";
/*   |   */    }
/*   |   */    
/*   |   */    //For tShow
/*   |   */    public String getReplacementInfo() {
/*   |   */        return "false";
/*   |   */    }
/*   |   */
/*   |   */}

