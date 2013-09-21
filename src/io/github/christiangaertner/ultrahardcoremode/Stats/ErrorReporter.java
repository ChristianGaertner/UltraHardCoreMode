/*
 * 
 * 
 */
package io.github.christiangaertner.ultrahardcoremode.Stats;

import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import static io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode.LOGGER;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Christian
 */
public class ErrorReporter {
    
    private final UltraHardCoreMode plugin;
    
    public ErrorReporter(UltraHardCoreMode plugin) {
        this.plugin = plugin;
        
    }
    
    public void addStacktrace(Exception e){
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
     
        try {
            //sanatize stacktrace
            String err = java.net.URLEncoder.encode(errors.toString(), "ISO-8859-1");
            
            String err1 = null;
            String err2 = null;
            String err3 = null;
            if (err.length() > 500) {
                err1 = "1>>>" + err.substring(0, 496);
                if (err.length() > 992) {
                    err2 = "2>>>" + err.substring(496, 992);
                    if (err.length() > 1488) {
                        err3 = "3>>>" + err.substring(992, 1488);
                    }
                }
            }
            
            if (err1 != null) {
                this.plugin.dastats.sendStackTrace(err1);
            }
            if (err2 != null) {
                this.plugin.dastats.sendStackTrace(err2);
            }
            if (err3 != null) {
                this.plugin.dastats.sendStackTrace(err3);
            }
 
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "[UHC] Cannot submit to DaStats.");
        }
    }

    
    

    
}
