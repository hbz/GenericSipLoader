package de.nrw.hbz.genericSipLoader.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Class TimeStampProvider
 * 
 * <p><em>Title: Time Stamp for directories or Files </em></p>
 * <p>Description: Class returns the actual time stamp which can be used for 
 * creation of unique directory and file names</p>
 * 
 * @author aquast, email
 * creation date: 29.07.2013
 *
 */
public class TimeStampProvider {

	// Initiate Logger for TimeStampProvider
  final static Logger logger = LogManager.getLogger(TimeStampProvider.class);

	/**
	 * <p><em>Title: </em></p>
	 * <p>Description: simple Method that returns a time stamp used to 
	 * create unique identifiers</p>
	 * 
	 * @return 
	 */
	public static String getTimeStamp(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'kk:mm:ss' 'z");
		Calendar cal = Calendar.getInstance();
		// logger.debug(dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
	}
		

}
