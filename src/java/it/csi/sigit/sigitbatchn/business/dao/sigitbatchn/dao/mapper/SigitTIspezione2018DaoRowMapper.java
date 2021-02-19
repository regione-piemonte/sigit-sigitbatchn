package it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dao.mapper;

import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.*;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dao.*;
import it.csi.sigit.sigitbatchn.business.dao.impl.BaseDaoRowMapper;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dao.impl.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper specifico del DAO SigitTIspezione2018Dao
 * @generated
 */
public class SigitTIspezione2018DaoRowMapper extends BaseDaoRowMapper
		implements
			org.springframework.jdbc.core.RowMapper {

	/**
	 * Dao associato al RowMapper. Serve per i supplier DAO
	 * @generated
	 */
	SigitTIspezione2018DaoImpl dao;

	/**
	 * costruttore
	 * @param columnsToRead elenco delle colonne da includere nel mapping (per query
	 *        incomplete, esempio distinct, custom select...) nella classe padre
	 */
	public SigitTIspezione2018DaoRowMapper(String[] columnsToRead, Class dtoClass, SigitTIspezione2018Dao dao) {
		super(columnsToRead, dtoClass);
		this.dao = (SigitTIspezione2018DaoImpl) dao;
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return SigitTIspezione2018Dto
	 * @generated
	 */
	public Object mapRow(ResultSet rs, int row) throws SQLException {
		Object dtoInstance = getNewDto();

		if (dtoInstance instanceof SigitTIspezione2018Dto) {
			return mapRow_internal((SigitTIspezione2018Dto) dtoInstance, rs, row);
		}

		if (dtoInstance instanceof SigitTIspezione2018ByCodiceFiscaleDto) {
			return mapRow_internal((SigitTIspezione2018ByCodiceFiscaleDto) dtoInstance, rs, row);
		}
		if (dtoInstance instanceof SigitTIspezione2018ByUtentiNonAttiviDto) {
			return mapRow_internal((SigitTIspezione2018ByUtentiNonAttiviDto) dtoInstance, rs, row);
		}

		return dtoInstance;
	}

	public SigitTIspezione2018Dto mapRow_internal(SigitTIspezione2018Dto objectToFill, ResultSet rs, int row)
			throws SQLException {
		SigitTIspezione2018Dto dto = objectToFill;

		// colonna [ID_ISPEZIONE_2018]
		if (mapAllColumns || columnsToReadMap.get("ID_ISPEZIONE_2018") != null)
			dto.setIdIspezione2018((Integer) rs.getObject("ID_ISPEZIONE_2018"));

		// colonna [FK_STATO_ISPEZIONE]
		if (mapAllColumns || columnsToReadMap.get("FK_STATO_ISPEZIONE") != null)
			dto.setFkStatoIspezione(rs.getBigDecimal("FK_STATO_ISPEZIONE"));

		// colonna [FK_VERIFICA]
		if (mapAllColumns || columnsToReadMap.get("FK_VERIFICA") != null)
			dto.setFkVerifica((Integer) rs.getObject("FK_VERIFICA"));

		// colonna [FK_ACCERTAMENTO]
		if (mapAllColumns || columnsToReadMap.get("FK_ACCERTAMENTO") != null)
			dto.setFkAccertamento((Integer) rs.getObject("FK_ACCERTAMENTO"));

		// colonna [CODICE_IMPIANTO]
		if (mapAllColumns || columnsToReadMap.get("CODICE_IMPIANTO") != null)
			dto.setCodiceImpianto(rs.getBigDecimal("CODICE_IMPIANTO"));

		// colonna [CF_ISPETTORE_SECONDARIO]
		if (mapAllColumns || columnsToReadMap.get("CF_ISPETTORE_SECONDARIO") != null)
			dto.setCfIspettoreSecondario(rs.getString("CF_ISPETTORE_SECONDARIO"));

		// colonna [DT_CREAZIONE]
		if (mapAllColumns || columnsToReadMap.get("DT_CREAZIONE") != null)
			dto.setDtCreazione(rs.getTimestamp("DT_CREAZIONE"));

		// colonna [ENTE_COMPETENTE]
		if (mapAllColumns || columnsToReadMap.get("ENTE_COMPETENTE") != null)
			dto.setEnteCompetente(rs.getString("ENTE_COMPETENTE"));

		// colonna [FLG_ESITO]
		if (mapAllColumns || columnsToReadMap.get("FLG_ESITO") != null)
			dto.setFlgEsito(rs.getBigDecimal("FLG_ESITO"));

		// colonna [DT_SVEGLIA]
		if (mapAllColumns || columnsToReadMap.get("DT_SVEGLIA") != null)
			dto.setDtSveglia(rs.getTimestamp("DT_SVEGLIA"));

		// colonna [NOTE_SVEGLIA]
		if (mapAllColumns || columnsToReadMap.get("NOTE_SVEGLIA") != null)
			dto.setNoteSveglia(rs.getString("NOTE_SVEGLIA"));

		// colonna [NOTE]
		if (mapAllColumns || columnsToReadMap.get("NOTE") != null)
			dto.setNote(rs.getString("NOTE"));

		// colonna [DT_CONCLUSIONE]
		if (mapAllColumns || columnsToReadMap.get("DT_CONCLUSIONE") != null)
			dto.setDtConclusione(rs.getTimestamp("DT_CONCLUSIONE"));

		// colonna [ISTAT_PROV_COMPETENZA]
		if (mapAllColumns || columnsToReadMap.get("ISTAT_PROV_COMPETENZA") != null)
			dto.setIstatProvCompetenza(rs.getString("ISTAT_PROV_COMPETENZA"));

		// colonna [FLG_ACC_SOSTITUTIVO]
		if (mapAllColumns || columnsToReadMap.get("FLG_ACC_SOSTITUTIVO") != null)
			dto.setFlgAccSostitutivo(rs.getBigDecimal("FLG_ACC_SOSTITUTIVO"));

		// colonna [FLG_ISP_PAGAMENTO]
		if (mapAllColumns || columnsToReadMap.get("FLG_ISP_PAGAMENTO") != null)
			dto.setFlgIspPagamento(rs.getBigDecimal("FLG_ISP_PAGAMENTO"));

		return dto;
	}

	/**
	 * Metodo specifico di mapping relativo al DTO custom SigitTIspezione2018ByCodiceFiscaleDto.
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return SigitTIspezione2018ByCodiceFiscaleDto
	 * @generated
	 */

	public SigitTIspezione2018ByCodiceFiscaleDto mapRow_internal(SigitTIspezione2018ByCodiceFiscaleDto objectToFill,
			ResultSet rs, int row) throws SQLException {
		SigitTIspezione2018ByCodiceFiscaleDto dto = objectToFill;

		if (mapAllColumns || columnsToReadMap.get("ID_ISPEZIONE_2018") != null)
			dto.setIs_2018IdIspezione2018((Integer) rs.getObject("ID_ISPEZIONE_2018"));

		if (mapAllColumns || columnsToReadMap.get("DT_SVEGLIA") != null)
			dto.setIs_2018DtSveglia(rs.getTimestamp("DT_SVEGLIA"));

		if (mapAllColumns || columnsToReadMap.get("NOTE_SVEGLIA") != null)
			dto.setIs_2018NoteSveglia(rs.getString("NOTE_SVEGLIA"));

		return dto;
	}

	/**
	 * Metodo specifico di mapping relativo al DTO custom SigitTIspezione2018ByUtentiNonAttiviDto.
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return SigitTIspezione2018ByUtentiNonAttiviDto
	 * @generated
	 */

	public SigitTIspezione2018ByUtentiNonAttiviDto mapRow_internal(SigitTIspezione2018ByUtentiNonAttiviDto objectToFill,
			ResultSet rs, int row) throws SQLException {
		SigitTIspezione2018ByUtentiNonAttiviDto dto = objectToFill;

		if (mapAllColumns || columnsToReadMap.get("ID_ISPEZIONE_2018") != null)
			dto.setTIdIspezione2018((Integer) rs.getObject("ID_ISPEZIONE_2018"));

		if (mapAllColumns || columnsToReadMap.get("NOME") != null)
			dto.setVNome(rs.getString("NOME"));

		if (mapAllColumns || columnsToReadMap.get("COGNOME") != null)
			dto.setVCognome(rs.getString("COGNOME"));

		if (mapAllColumns || columnsToReadMap.get("CODICE_FISCALE") != null)
			dto.setVCodiceFiscale(rs.getString("CODICE_FISCALE"));

		if (mapAllColumns || columnsToReadMap.get("DT_SVEGLIA") != null)
			dto.setTDtSveglia(rs.getTimestamp("DT_SVEGLIA"));

		if (mapAllColumns || columnsToReadMap.get("NOTE_SVEGLIA") != null)
			dto.setTNoteSveglia(rs.getString("NOTE_SVEGLIA"));

		if (mapAllColumns || columnsToReadMap.get("ID_RUOLO_PA") != null)
			dto.setAIdRuoloPa((Integer) rs.getObject("ID_RUOLO_PA"));

		if (mapAllColumns || columnsToReadMap.get("ISTAT_ABILITAZIONE") != null)
			dto.setAIstatAbilitazione(rs.getString("ISTAT_ABILITAZIONE"));

		if (mapAllColumns || columnsToReadMap.get("MAIL_COMUNICAZIONE") != null)
			dto.setAMailComunicazione(rs.getString("MAIL_COMUNICAZIONE"));

		return dto;
	}

}
