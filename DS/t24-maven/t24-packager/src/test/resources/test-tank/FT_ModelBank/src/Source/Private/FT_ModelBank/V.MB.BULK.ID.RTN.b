*-----------------------------------------------------------------------------
* <Rating>-30</Rating>
*-----------------------------------------------------------------------------
    SUBROUTINE V.MB.BULK.ID.RTN
*-----------------------------------------------------------------------------
* This is an id routine that is used to set the id for FT.BULK.ITEM records
* based on the BULK MASTER .


    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_System

    GOSUB INITIALISE
    GOSUB PROCESS
    RETURN

*-----------------------------------------------------------------------------
INITIALISE:

*-----------------------------------------------------------------------------
* Initialise the variables and open the files

    FN.FT.BULK.ITEM = 'F.FT.BULK.ITEM'
    F.FT.BULK.ITEM = ''
    CALL OPF(FN.FT.BULK.ITEM,F.FT.BULK.ITEM)


    BULK.ID = ""
    RETURN

*-----------------------------------------------------------------------------
PROCESS:
*-----------------------------------------------------------------------------
* Find out the next bulk item id from the bulk master id
    BULK.ID.ORIG = System.getVariable('CURRENT.BULK.MASTER')
    ID.CNT = "0"
    ERR.FLAG = "0"

    LOOP
    WHILE ERR.FLAG = "0"
        ID.CNT = ID.CNT + 1
        BULK.ID = BULK.ID.ORIG:'.':ID.CNT
        GOSUB FETCH.ID

    REPEAT


    COMI = BULK.ID.ORIG:'.':ID.CNT
    RETURN

*-----------------------------------------------------------------------------
FETCH.ID:
*-----------------------------------------------------------------------------

    CALL F.READ(FT.BULK.ITEM,BULK.ID,R.FT.BULK.ITEM,F.FT.BULK.ITEM,R.FT.BULK.ERROR)
    IF R.FT.BULK.ERROR THEN
        ERR.FLAG = -1
    END
    RETURN

*-----------------------------------------------------------------------------
