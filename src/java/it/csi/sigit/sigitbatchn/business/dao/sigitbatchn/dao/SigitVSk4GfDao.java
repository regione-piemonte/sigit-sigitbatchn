package it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dao;

import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dao.*;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.*;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.qbe.*;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.metadata.*;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.exceptions.*;
import it.csi.sigit.sigitbatchn.business.dao.util.*;
import it.csi.sigit.sigitbatchn.business.dao.qbe.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interfaccia pubblica del DAO sigitVSk4Gf.
 * Espone le operazioni che possono essere eseguite per la gestione 
 * della tabella [Table[TRANSIENT]]
 * @generated
 */
public interface SigitVSk4GfDao {

	/** 
	 * Implementazione del finder byCodImpiantoOrdered
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<SigitVSk4GfDto> findByCodImpiantoOrdered(Integer input) throws SigitVSk4GfDaoException;

	/** 
	 * Implementazione del finder appAttiviByCodImpiantoProgressivi
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<SigitVSk4GfDto> findAppAttiviByCodImpiantoProgressivi(
			it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.filter.CompFilter input) throws SigitVSk4GfDaoException;

	/** 
	 * Implementazione del finder attiviByCodImpiantoProgressivi
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<SigitVSk4GfDto> findAttiviByCodImpiantoProgressivi(
			it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.filter.CompFilter input) throws SigitVSk4GfDaoException;

}
