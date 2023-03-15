package com.synergy.synergydhrishtiplus.server_pack.writable;

public enum Commands {
     READ_ATGLEVEL_PROGAGE("4D32333837300D0A"),
     READ_LEVEL("4D32333837300D0A"),
     READ_DMP_PRO("4D33313734330D0A"),
//    GVRDISPENSER_STATUS("0141537f6c"),

    //GVR COMMAND
      get_status_pollg("01"),
    get_authrization_commandg("11"),
    get_read_transaction_logg("61"),
    get_read_transactiong("41"),
    get_totalizerg("51"),


    statusPoll("53"),
    authrization_command("41"),
    read_lasttransaction("514713"),
    read_transaction("52"),
    clear_salecommands("46"),
    suspend_salecommand("44"),
    pump_startcommands("4f"),
    pumps_stopcommands("5a"),
    totaliser_command("54"),
    set_presetCommand("50"),
    read_preset("48"),



    get_status_poll ("0141537f"),
    get_authrization_command("0141417f"),
    get_read_transaction_log("01415147137f"),
    get_read_transaction("0141527f"),
    READ_VOL_TOTALIZER("0141547F6B"),
    clear_sale("0141467f"),
    suspend_sale("0141447f"),
    pump_start("01414f7f"),
    pump_stop("01415a7f"),
    get_totalizer("0141547f"),
    CLEAR_285("\r\n"),
 /// TOKHEIUM FOR SECOND NOZZLE
    get_status_poll2 ("0241537f"),
    get_authrization_command2("0241417f"),
    get_read_transaction_log2("02415147137f"),
    get_read_transaction2("0241527f"),
    READ_VOL_TOTALIZER2("0241547F6B"),
    clear_sale2("0241467f"),
    suspend_sale2("0241447f"),
    pump_start2("02414f7f"),
    pump_stop2("02415a7f"),
    get_totalizer2("0241547f"),
  /// FOR THIRD NOZZLE
   get_status_poll3 ("0341537f"),
    get_authrization_command3("0341417f"),
    get_read_transaction_log3("03415147137f"),
    get_read_transaction3("0341527f"),
    READ_VOL_TOTALIZER3("0341547F6B"),
    clear_sale3("0341467f"),
    suspend_sale3("0341447f"),
    pump_start3("03414f7f"),
    pump_stop3("03415a7f"),
    get_totalizer3("0341547f"),
  /// FOR FOURTH NOZZLE
    get_status_poll4 ("0341537f"),
    get_authrization_command4("0341417f"),
    get_read_transaction_log4("03415147137f"),
    get_read_transaction4("0341527f"),
    READ_VOL_TOTALIZER4("0341547F6B"),
    clear_sale4("0341467f"),
    suspend_sale4("0341447f"),
    pump_start4("03414f7f"),
    pump_stop4("03415a7f"),
    get_totalizer4("0341547f");
    private final String name;
    Commands(String s) {
        name = s;
    }
    public String toString() {
        return name;
    }
    public static String getEnumByString(String code) {
        for (Commands e : Commands.values()) {
            if (code == e.name) return e.name();
        }
        return null;
    }
}
