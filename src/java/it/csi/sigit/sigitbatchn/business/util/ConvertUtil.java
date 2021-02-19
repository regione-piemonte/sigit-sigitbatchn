/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2021
 *******************************************************************************/
package it.csi.sigit.sigitbatchn.business.util;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlCalendar;

/**
 * Utility di conversione
 * 
 * @author
 */
public class ConvertUtil extends GenericUtil {
	/**
	 * Formato data standard
	 */
	public static final String FORMAT_DATE_STANDARD = "dd/MM/yyyy";
	
	/**
	 * Formato data standard completa
	 */
	public static final String FORMAT_DATE_STANDARD_COMPLETA = "dd/MM/yyyy hh:mm:ss";
	
	/**
	 * Formato data con underscore
	 */
	public static final String FORMAT_DATE_UNDERSCORE = "dd_MM_yyyy";
	/**
	 * Formattatore data
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_STANDARD);
	
	/**
	 * Formattatore data completa
	 */
	private static final SimpleDateFormat sdfc = new SimpleDateFormat(FORMAT_DATE_STANDARD_COMPLETA);
	/**
	 * Lista dei formati decimali
	 */
	private static Hashtable<Integer, String> dicimalFormats = new Hashtable<Integer, String>();

	
	/**
	 * Converte una data in stringa
	 * 
	 * @param dt Data da convertire
	 * @return Data convertita
	 */
	public static String convertToString(java.util.Date dt) {
		String converted = null;

		if(dt != null) {
			converted = sdf.format(dt);
		}
		return converted;
	}

	/**
	 * Converte una data in stringa
	 * 
	 * @param dt Data da convertire
	 * @return Data convertita
	 */
	public static String convertToStringCompleta(java.util.Date dt) {
		String converted = null;

		if(dt != null) {
			converted = sdfc.format(dt);
		}
		return converted;
	}

	/**
	 * Converte una data in stringa
	 * 
	 * @param dt Data da convertire
	 * @return Data convertita
	 */
	public static String convertToString(Calendar dt) {
		String converted = null;

		if(dt != null) {
			converted = sdf.format(dt.getTime());
		}
		return converted;
	}

	/**
	 * Converte una data in stringa
	 * 
	 * @param dt Data da convertire
	 * @return Data convertita
	 */
	public static String convertToString(Calendar dt, String format) {
		String converted = null;

		if(dt != null) {
			converted = convertToString(dt.getTime(), format);
		}
		return converted;
	}

	
	/**
	 * Converte una data in stringa
	 * 
	 * @param dt Data da convertire
	 * @return Data convertita
	 */
	public static Date convertToDate(Calendar dt) {
		Date converted = null;

		if(dt != null) {
			converted = new Date(dt.getTimeInMillis());
		}
		return converted;
	}
	/**
	 * Converte una data in stringa
	 * 
	 * @param dt Data da convertire
	 * @param format Formato da utilizzare
	 * @return Data convertita
	 */
	public static String convertToString(java.util.Date dt, String format) {
		SimpleDateFormat sdf = null;
		String converted = null;

		if(dt != null) {
			sdf = new SimpleDateFormat(format);
			converted = sdf.format(dt);
		}
		return converted;
	}

	/**
	 * Converte un Boolean in stringa
	 * 
	 * @param b Boolean da convertire
	 * @return Boolean convertito
	 */
	public static String convertToString(Boolean b) {
		
		String converted = (b!=null&&b)?Constants.LABEL_SI:Constants.LABEL_NO;
		
		return converted;
	}

	/**
	 * Converte una stringa in data
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 * @throws Exception Stringa non valida
	 */
	public static java.util.Date convertToDate(String s) throws Exception {
		java.util.Date converted = null;

		if(StringUtils.isNotBlank(s)) {
			try {
				converted = sdf.parse(s);
			}
			catch(ParseException e) {
				throw new Exception("Errore nella parsificazioen della data " + s, e);
			}
		}
		return converted;
	}
	
	public static java.util.Date convertToDate(String s, String pattern) throws Exception {
		java.util.Date converted = null;
		SimpleDateFormat sd = new SimpleDateFormat(pattern);

		if(StringUtils.isNotBlank(s)) {
			try {
				converted = sd.parse(s);
			}
			catch(ParseException e) {
				throw new Exception("Errore nella parsificazioen della data " + s, e);
			}
		}
		return converted;
	}

	public static java.sql.Date convertToSqlDate(String s, String pattern) throws Exception {
		java.sql.Date converted = null;
		
		SimpleDateFormat sd = new SimpleDateFormat(pattern);
		if(StringUtils.isNotBlank(s)) {
			try {
				converted = new Date(sd.parse(s).getTime());
			}
			catch(ParseException e) {
				throw new Exception("Errore nella parsificazione della data " + s, e);
			}
		}
		return converted;
	}

	/**
	 * Converte una stringa in data SQL
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static Date convertToSqlDate(String s) {
		Date converted = null;

		if(StringUtils.isNotBlank(s)) {
			try {
				converted = new Date(sdf.parse(s).getTime());
			}
			catch(ParseException e) {
				log.debug("Stringa da convertire in sql.Date: " + s);
			}
		}
		return converted;
	}

	/**
	 * Converte una stringa in data SQL
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static Date convertToSqlDate(java.util.Date data) {
		Date converted = null;

		if(data != null) {
			try {
				converted = new Date(data.getTime());
			}
			catch(Exception e) {
				log.debug("util.Date da convertire in sql.Date: " + data);
			}
		}
		return converted;
	}
	
	/**
	 * Converte una stringa in data SQL
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static Date convertToSqlDateFull(String s) {
		Date converted = null;

		if(StringUtils.isNotBlank(s)) {
			converted = new Date(new java.util.Date().getTime());
		}
		return converted;
	}
	
	/**
	 * Converte una stringa in stringa SQL
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static String convertToSqlString(String data) {
		String converted = null;

		if(GenericUtil.isNotNullOrEmpty(data)) {
			try {
				converted = data.trim();
			}
			catch(Exception e) {
				log.debug("String da convertire in String 'pulita': " + data);
			}
		}
		return converted;
	}
	
	/**
	 * Converte un {@link Number} in stringa
	 * 
	 * @param i {@link Number} da convertire
	 * @return {@link Number} convertito
	 */
	public static String convertToString(Number i) {
		String converted = null;

		if(i != null) {
			converted = i.toString();
			// Se il numero ha la virgola, si sostituisce l'eventuale punto con
			// la virgola
			converted = converted.replace(".", ",");
		}
		return converted;
	}

	/**
	 * Converte un {@link Integer} in stringa
	 * 
	 * @param i {@link Integer} da convertire
	 * @return {@link Integer} convertito
	 */
	public static String convertToString(Integer i) {
		String converted = null;

		if(i != null) {
			converted = i.toString();
		}
		return converted;
	}

	/**
	 * Converte un {@link BigDecimal} in stringa
	 * 
	 * @param number Numero da convertire
	 * @param decimali Decimali da visualizzare
	 * @return Numero convertito
	 */
	public static String convertToString(BigDecimal number, int decimali) {
		DecimalFormat formatter = null;
		String format = null;
		String stringValue = null;

		if(number != null) {
			format = getNumberFormat(decimali);
			formatter = new DecimalFormat(format);
			stringValue = formatter.format(number.doubleValue());
		}
		return stringValue;
	}
	
	/**
	 * Converte un {@link BigDecimal} in stringa
	 * 
	 * @param number Numero da convertire
	 * @return Numero convertito
	 */
	/*public static String convertToString(BigDecimal num) {
		return convertToString(num, null);
	}*/

	public static String convertToString(BigDecimal num, String pattern) {
		if (num != null) {
			DecimalFormat df = null;
			if (pattern == null) {
				df = new DecimalFormat();
			} else {
				df = new DecimalFormat(pattern);
			}
			return df.format(num.doubleValue());
		} else {
			return "";
		}
	}

	/**
	 * Converte un {@link BigDecimal} in stringa
	 * 
	 * @param number Numero da convertire
	 * @param decimali Decimali da visualizzare
	 * @return Numero convertito
	 */
	public static String convertToString(Double number, int decimali) {
		DecimalFormat formatter = null;
		String format = null;
		String stringValue = null;

		if(number != null) {
			format = getNumberFormat(decimali);
			formatter = new DecimalFormat(format);
			stringValue = formatter.format(number.doubleValue());
		}
		return stringValue;
	}
	
	/**
	 * Converte un {@link Enum} in stringa
	 * 
	 * @param enumValue Enum da convertire
	 * @return Enum convertito
	 */
	/*
	public static String convertToString(Enum enumValue) {
		String stringValue = null;

		if(enumValue != null) {
			stringValue = enumValue.name();
			
		}
		return stringValue;
	}
	*/
	
	/**
	 * Restituisce il formato numerico richiesto
	 * 
	 * @param decimali Decimali da visualizzare
	 * @return Formato numerico
	 */
	private static String getNumberFormat(int decimali) {
		String format = null;
		String decimal = null;
		int decimaliCount;

		format = dicimalFormats.get(decimali);
		if(format == null) {
			format = "#,##0";
			decimal = "";
			for(decimaliCount = 0; decimaliCount < decimali; decimaliCount++) {
				decimal += "0";
			}
			if(decimal.length() > 0) {
				format += ("." + decimal);
			}
			dicimalFormats.put(decimali, format);
		}
		return format;
	}

	/**
	 * Converte una stringa in un {@link BigInteger}
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static BigInteger convertToBigInteger(String s) {
		BigInteger converted = null;

		if(StringUtils.isNotBlank(s)) {
			try {
				converted = new BigInteger(s);
			}
			catch(Exception e) {
				log.error("Errore durante la conversione di '" + s + "' in BigInteger: " + s, e);
			}
		}
		return converted;
	}

	public static BigInteger convertToBigInteger(BigDecimal b) {
		BigInteger converted = null;
		
		if(b!=null) {
			try {
				converted = new BigInteger(b.toString());
			}
			catch(Exception e) {
				log.error("Errore durante la conversione di '" + b + "' in BigInteger: " + b, e);
			}
		}
		return converted;
	}

	/**
	 * Converte una stringa in un {@link Integer}
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static Integer convertToInteger(String s) {
		Integer converted = null;

		if(StringUtils.isNotBlank(s)) {
			try {
				converted = new Integer(s);
			}
			catch(Exception e) {
				log.error("Errore durante la conversione di '" + s + "' in Integer: " + s, e);
			}
		}
		return converted;
	}

	
	/**
	 * Converte un {@link BigDecimal} in un {@link Integer}
	 * 
	 * @param i {@link BigDecimal} da convertire
	 * @return Stringa convertita
	 */
	public static Integer convertToInteger(BigInteger i) {
		Integer converted = null;

		if(i != null) {
			converted = i.intValue();
		}
		return converted;
	}

	
	/**
	 * Converte una stringa in un {@link Double}
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static Double convertToDouble(String s) {
		Double converted = null;

		if(StringUtils.isNotBlank(s)) {
			try {
				s = s.replace(",", ".");
				converted = new Double(s);
			}
			catch(Exception e) {
				log.error("Errore durante la conversione di '" + s + "' in Double: " + s, e);
			}
		}
		return converted;
	}

	/**
	 * Converte una stringa in un {@link Float}
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static Float convertToFloat(String s) {
		Float converted = null;

		if(StringUtils.isNotBlank(s)) {
			try {
				converted = new Float(s);
			}
			catch(Exception e) {
				log.error("Errore durante la conversione di '" + s + "' in Float: " + s, e);
			}
		}
		return converted;
	}

	/**
	 * Converte un {@link BigDecimal} in un {@link Float}
	 * 
	 * @param i {@link BigDecimal} da convertire
	 * @return {@link BigDecimal} convertita
	 */
	public static Float convertToFloat(BigDecimal i) {
		Float converted = null;

		if(i != null) {
			converted = i.floatValue();
		}
		return converted;
	}

	/**
	 * Converte una stringa in un {@link Long}
	 * 
	 * @param s Stringa da convertire
	 * @return Stringa convertita
	 */
	public static Long convertToLong(String s) {
		Long converted = null;

		if(StringUtils.isNotBlank(s)) {
			try {
				converted = new Long(s);
			}
			catch(Exception e) {
				log.error("Errore durante la conversione di '" + s + "' in Long: " + s, e);
			}
		}
		return converted;
	}

	/**
	 * Converte una stringa in {@link BigDecimal}. Accetta anche numeri con la
	 * virgola come separatore dei decimali. Non sono ammessi separatori di
	 * migliaia
	 * 
	 * @param s Valore da convertire
	 * @return {@link BigDecimal} che rappresenta il valore
	 */
	public static BigDecimal convertToBigDecimal(String s) {
		BigDecimal number = null;

		if(StringUtils.isNotBlank(s)) {
			try {
				// Si converte l'eventuale virgola in punto
				number = new BigDecimal(s.replaceAll(",", "."));
			}
			catch(Exception e) {
				log.error("Errore durante la conversione di '" + s + "' in BigDecimal: " + s, e);
			}
		}
		return number;
	}

	/**
	 * Converte una stringa in {@link BigDecimal}. Accetta anche numeri con la
	 * virgola come separatore dei decimali. Non sono ammessi separatori di
	 * migliaia
	 * 
	 * @param s Valore da convertire
	 * @return {@link BigDecimal} che rappresenta il valore
	 */
	public static BigDecimal convertToBigDecimal(Long l) {
		BigDecimal b = null;

		if(l != null) {
			b = BigDecimal.valueOf(l.longValue());
		}
		return b;
	}

	
	/**
	 * Converte un {@link Boolean} in un {@link BigDecimal}
	 * 
	 * @param i {@link Boolean} da convertire
	 * @return {@link BigDecimal} convertito
	 */
	public static BigDecimal convertToBigDecimal(Boolean i) {
		BigDecimal b = null;

		if(i != null) {

			b = i?new BigDecimal(Constants.SI_1):new BigDecimal(Constants.NO_0);
			
		}
		return b;
	}
	
	/**
	 * Riduce i decimali di un BigDecimal in abse al numeor di decimali massimo
	 * 
	 * @param number BigDecimal da trattare
	 * @param numeroCifreDecimali numero di decimali max
	 * @return Stringa convertita
	 */
	public static BigDecimal convertToBigDecimal(BigDecimal number, int numeroCifreDecimali)
	{
		if (number != null)
		{
			number = number.setScale(numeroCifreDecimali, BigDecimal.ROUND_HALF_UP);
		}
		
		return number;
	}
	/**
	 * Converte un {@link BigDecimal} in un {@link Integer}
	 * 
	 * @param i {@link BigDecimal} da convertire
	 * @return Stringa convertita
	 */
	public static Integer convertToInteger(BigDecimal i) {
		Integer converted = null;

		if(i != null) {
			converted = i.intValue();
		}
		return converted;
	}

	/**
	 * Converte un {@link BigDecimal} in un {@link Long}
	 * 
	 * @param b {@link BigDecimal} da convertire
	 * @return Stringa convertita
	 */
	public static Long convertToLong(BigDecimal b) {
		Long converted = null;

		if(b != null) {
			converted = b.longValue();
		}
		return converted;
	}

	/**
	 * Converte un {@link Integer} in un {@link BigDecimal}
	 * 
	 * @param i {@link Integer} da convertire
	 * @return {@link Integer} convertito
	 */
	public static BigDecimal convertToBigDecimal(Integer i) {
		BigDecimal b = null;

		if(i != null) {
			b = BigDecimal.valueOf(i);
		}
		return b;
	}

	/**
	 * Converte un {@link Number} in un {@link BigDecimal}
	 * 
	 * @param i {@link Number} da convertire
	 * @return {@link Number} convertito
	 */
	public static BigDecimal convertToBigDecimal(Number i) {
		BigDecimal b = null;
		
		if(i != null) {
			b = convertToBigDecimal(i.toString());
		}
		return b;
	}

	/**
	 * Converte un {@link Float} in un {@link BigDecimal}
	 * 
	 * @param f {@link Float} da convertire
	 * @return {@link Float} convertito
	 */
	public static BigDecimal convertToBigDecimal(Float f) {
		return BigDecimal.valueOf(f);
	}

	/**
	 * Converte un {@link String} in un {@link Boolean}
	 * 
	 * @param s {@link String} da convertire
	 * @return {@link String} convertito
	 */
	public static Boolean convertToBoolean(String s) {
		if (s == null || s.length() == 0) {
			return null;
		} else {
			try {
				if (s.equalsIgnoreCase(Constants.SI)) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * Converte un {@link BigDecimal} in un {@link Boolean}
	 * 
	 * @param s {@link BigDecimal} da convertire
	 * @return {@link BigDecimal} convertito
	 */
	public static Boolean convertToBoolean(BigDecimal s) {
		if (s == null) {
			return null;
		} else {
			try {
				if (s.intValue() == Constants.SI_1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * Converte un {@link BigDecimal} in un {@link boolean}
	 * 
	 * @param s {@link BigDecimal} da convertire
	 * @return {@link BigDecimal} convertito
	 */
	public static boolean convertToBooleanAllways(BigDecimal s) {
		if (s == null) {
			return false;
		} else {
			try {
				if (s.intValue() == Constants.SI_1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	/**
	 * Converte un {@link Boolean} in un {@link boolean}
	 * 
	 * @param s {@link Boolean} da convertire
	 * @return {@link Boolean} convertito
	 */
	public static boolean convertToBooleanAllways(Boolean s) {
		if (s == null) {
			return false;
		} else {
			try {
				if (s.booleanValue()) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	
	/**
	 * Converte un {@link BigDecimal} in un float
	 * 
	 * @param b {@link BigDecimal} da convertire
	 * @return Stringa convertita
	 */
	public static float convertToFloatPrimitive(BigDecimal b) {
		float f = 0;

		if(b != null) {
			f = convertToFloat(b);
		}
		return f;
	}

	/**
	 * Restituisce l'anno di una data
	 * 
	 * @param value Data da cui estrarre l'anno
	 * @return Anno della data
	 * @throws Exception Errore durante la conversione della data
	 */
	public static int getYear(String value) throws Exception {
		return getYear(convertToDate(value));
	}

	/**
	 * Restituisce l'anno di una data
	 * 
	 * @param date Data da cui estrarre l'anno
	 * @return Anno della data
	 */
	public static int getYear(java.util.Date date) {
		GregorianCalendar calendar = null;

		calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Restituisce il flag PDF a partire da un {@link BigDecimal}
	 * 
	 * @param b {@link BigDecimal} da cui recuperare il flag
	 * @return Flag PDF ottenuto dal {@link BigDecimal}
	 */
	public static int flagPdf(BigDecimal b) {
		int result = 0;

		if(b != null) {
			result = b.intValue();
		}
		return result;
	}

	/**
	 * Restituisce il flag PDF a partire da una {@link String}
	 * 
	 * @param s {@link String} da cui recuperare il flag
	 * @return Flag PDF ottenuto dal {@link String}
	 */
	public static int flagPdf(String s) {
		int result = 0;

		if(StringUtils.isNotEmpty(s)) {
			result = Integer.parseInt(s);
		}
		return result;
	}

	/**
	 * Restituisce il flag PDF a partire da una {@link String}
	 * 
	 * @param s {@link String} da cui recuperare il flag
	 * @return Flag PDF ottenuto dalla {@link String}
	 */
	public static String flagPdfString(String s) {
		if(StringUtils.isEmpty(s)) {
			s = "";
		}
		return s;
	}

	/**
	 * Restituisce il flag PDF a partire da un {@link Integer}
	 * 
	 * @param i {@link Integer} da cui recuperare il flag
	 * @return Flag PDF ottenuto dall'{@link Integer}
	 */
	public static String flagPdfString(Integer i) {
		String s = null;

		if(i == null) {
			s = "";
		}
		else {
			s = i.toString();
		}
		return s;
	}

	/**
	 * Restituisce il flag PDF a partire da un {@link BigDecimal}
	 * 
	 * @param b {@link BigDecimal} da cui recuperare il flag
	 * @return Flag PDF ottenuto dal {@link BigDecimal}
	 */
	public static String flagPdfString(BigDecimal b) {
		String flag = null;

		if(b == null) {
			flag = "";
		}
		else {
			flag = convertToString(b);
		}
		return flag;
	}

	/**
	 * Restituisce il valore di una colonna intera
	 * 
	 * @param rs Result set da cui recuperare il valore
	 * @param columnIndex Indice della colonna
	 * @return Valore della colonna
	 */
	public static Integer getInteger(ResultSet rs, int columnIndex) {
		Integer value = null;

		try {
			value = rs.getInt(columnIndex);
			if(rs.wasNull()) {
				value = null;
			}
		}
		catch(SQLException e) {
		}
		return value;
	}

	/**
	 * Restituisce il valore di una colonna intera
	 * 
	 * @param rs Result set da cui recuperare il valore
	 * @param columnName Nome della colonna
	 * @return Valore della colonna
	 */
	public static Integer getInteger(ResultSet rs, String columnName) {
		Integer value = null;

		try {
			value = getInteger(rs, rs.findColumn(columnName));
		}
		catch(SQLException e) {
		}
		return value;
	}

	/**
	 * Converte un stringa in una stringa solo alfanumerica
	 * 
	 * @param s {@link String} da convertire
	 * @return Stringa convertita
	 */
	public static String convertToAlphanumericString(String s) {
		StringBuffer converted = new StringBuffer();
		
		char[] temp = s.toCharArray();
		for (int i = 0; i < temp.length; i++) {
			char c = temp[i];
//			new String({c});
			
			if (StringUtils.isAlphanumericSpace(new String(new char[]{c})))
			{
				converted.append(c);
			}
			else
			{
				converted.append(" ");
			}
		}

		return converted.toString();
	}
	
	public static XmlCalendar convertToXmlCalendar(Date data)
	{
		if(data==null)
			return null;
//		NON FUNZIONA CON IL FORMATO dd/MM/yyyy
		String dataS = convertToString(data,"yyyy-MM-dd");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			GregorianCalendar grc = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			XMLGregorianCalendar gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(grc);
			gc.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException e) {
		}
		XmlCalendar xd = new XmlCalendar(dataS);
		return xd;
	}

	public static XmlCalendar convertDateToXmlCalendar(java.util.Date data)
	{
		if(data==null)
			return null;
		return convertToXmlCalendar(new Date(data.getTime()));
	}
	
	public static String formattaSiglaBollino(BigDecimal numeroBollino){
		String value = "";
		if(numeroBollino!=null)
			value = Constants.SIGLA_BOLLINO_RP+"-"+numeroBollino;
		return value;
	}
	
	public static String formattaNominativo(String nome, String cognome){
		String value = "";
		boolean flg = false;
		if(nome!=null){
			value = nome;
			flg = true;
		}
		
		if(flg){
			value = value + " "+cognome;
		}else{
			value = cognome;
		}
		
		return value;
	}
	
	public static Date convertTimestampToDate(Timestamp timestamp){
		Date date = null;
		
		if (timestamp == null) {
			return date;
		} else {
			try {
				date = new Date(timestamp.getTime());
			} catch (Exception e) {
				return date;
			}
			return date;
		}
	}
	
	public static Timestamp convertDateToTimestamp(java.util.Date date){
		Timestamp timestamp = null;
		
		if (date == null) {
			return timestamp;
		} else {
			try {
				timestamp = new Timestamp(date.getTime());
			} catch (Exception e) {
				return timestamp;
			}
			return timestamp;
		}
	}

	public static String convertToString(Timestamp timestamp) {
		String converted = null;
		Date dt = convertTimestampToDate(timestamp);
		if(dt != null) {
			converted = sdf.format(dt);
		}
		return converted;
	}
	
}
