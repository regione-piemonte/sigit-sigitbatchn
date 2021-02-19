/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2021
 *******************************************************************************/
package it.csi.sigit.sigitbatchn.batch.timerservicememo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.csi.sigit.sigitbatchn.batch.AbstractBatch;
import it.csi.sigit.sigitbatchn.batch.BatchConf;
import it.csi.sigit.sigitbatchn.batch.BatchException;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitRImpRuoloPfpgDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitRPfRuoloPaDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTAccertamentoByUtentiNonAttiviDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTAccertamentoDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTAllegatoDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTIspezione2018ByCodiceFiscaleDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTIspezione2018ByUtentiNonAttiviDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTPersonaFisicaDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTPersonaGiuridicaDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTSanzioneAccertamentiByUtentiNonAttiviDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTSanzioneDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTSanzioneIspezione2018ByUtentiNonAttiviDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTVerificaByUtentiNonAttiviDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitTVerificaDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitVRicerca3ResponsabileDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitVRicercaAllegatiDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitWrkLogMemoDto;
import it.csi.sigit.sigitbatchn.business.dao.sigitbatchn.dto.SigitWrkLogMemoPk;
import it.csi.sigit.sigitbatchn.business.manager.DbMgr;
import it.csi.sigit.sigitbatchn.business.manager.ServiziMgr;
import it.csi.sigit.sigitbatchn.business.manager.SigitbatchMgr;
import it.csi.sigit.sigitbatchn.business.manager.util.DbManagerException;
import it.csi.sigit.sigitbatchn.business.util.Constants;
import it.csi.sigit.sigitbatchn.business.util.ConvertUtil;
import it.csi.sigit.sigitbatchn.business.util.GenericUtil;
import it.csi.sigit.sigitbatchn.business.util.InputAllegatiComp;

/**
 * Batch per recuperare le informazioni dei distributori
 * 
 */
public class TimerServiceMemoBatch extends AbstractBatch {
	/**
	 * Nome della classe
	 */
	private final static String CLASS_NAME = TimerServiceMemoBatch.class.getSimpleName();
	
	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE
			+ ".business");
	
	
	/**
	 * Manager business
	 */
	private SigitbatchMgr sigitbatchMgr = null;

	
	/**
	 * {@inheritDoc}
	 */
	public void execute() throws BatchException {
		final String METHOD_NAME = "execute";
		TimerServiceMemoConf conf = null;
		SigitbatchMgr sigitbatchMgr = getSigitbatchMgr();
		ServiziMgr serviziMgr = sigitbatchMgr.getServiziMgr();
		DbMgr dbMgr = sigitbatchMgr.getDbMgr();

		log.debug("[TimerServiceMemoBatch::execute] BEGIN");
		
		try {
			// Procedura “MEMO Sveglie PA”
			if (!dbMgr.cercaConfigValueFlg(Constants.CONFIG_KEY_CIT_MEMO_SVEGLIA_PA_ATTIVO)) {
				insertWrkLogMemo("CIT_MEMO_SVEGLIA_PA_ATTIVO non attivato");
			}
			else {
				inviaSveglieUtentiAttivi();
				inviaSveglieUtentiNonAttivi();
				insertWrkLogMemo("Procedura MEMO_SVEGLIA_PA conclusa");
			}
			
			//Procedura “MEMO scadenza Responsabile Impianto”
			if (!dbMgr.cercaConfigValueFlg(Constants.CONFIG_KEY_CIT_MEMO_SCADENZA_RI_ATTIVO)) {
				insertWrkLogMemo("CIT_MEMO_SCADENZA_RI_ATTIVO non attivato");
			}
			else {
				inviaSvegliaResponsabiliImpianti();
				insertWrkLogMemo("Procedura CIT_MEMO_SCADENZA_RI_ATTIVO conclusa");
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw new BatchException("Esecuzione del batch fallita", e);
		}
		
		log.debug("[TimerServiceMemoBatch::execute] END");
	}

	/**
	 * Restituisce il gestore dei servizi
	 * 
	 * @return Gestore dei servizi common
	 */
	
	public SigitbatchMgr getSigitbatchMgr() {
		return sigitbatchMgr;
	}

	/**
	 * Imposta il gestore dei servizi
	 * 
	 * @param commonSigitMgr Gestore dei servizi common
	 */
	
	public void setSigitbatchMgr(SigitbatchMgr sigitbatchMgr) {
		this.sigitbatchMgr = sigitbatchMgr;
	}
	
	/*
	private void testaInvioMail(SigitTImportDto sigitTImportDto)
	{
		try {
			getSigitbatchMgr().gestisciInvioMailImportOK(sigitTImportDto, null, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	*/
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getMailObject(BatchConf conf) throws BatchException {
		return conf.getOggettoMail();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getMailBody(BatchConf conf) throws BatchException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Inserisce sulla tabella SIGIT_WRK_LOG_MEMO un log
	 * @param description
	 * @return LogMemo
	 * @throws DbManagerException
	 */
	private SigitWrkLogMemoPk insertWrkLogMemo (String description) throws DbManagerException {
		SigitWrkLogMemoDto wrkLogMemo = new SigitWrkLogMemoDto();
		wrkLogMemo.setDtLogMemo(ConvertUtil.convertDateToTimestamp(new Date()));
		wrkLogMemo.setDescLogMemo(description);
		return getSigitbatchMgr().getDbMgr().insertWrkLogMemo(wrkLogMemo);
	}
	
	private SigitWrkLogMemoPk insertWrkLogMemo (Exception e) throws DbManagerException {
		String errorMessage = GenericUtil.getStackTrace(e);
		log.error(errorMessage);
		e.printStackTrace();
		if (errorMessage.length() > 1000) {
			errorMessage = errorMessage.substring(0, 999);
		}
		return insertWrkLogMemo(errorMessage);
	}
	

	private void insertWrkLogMemo(List<String> errors) throws DbManagerException {
		for (String error : errors) {
			insertWrkLogMemo(error);
		}	
	}
	
	/**
	 * Invia sveglie agli utenti attivi
	 * @throws DbManagerException
	 */
	private void inviaSveglieUtentiAttivi() throws DbManagerException {
		SigitbatchMgr sigitBatchMgr = getSigitbatchMgr();
		ServiziMgr serviziMgr = sigitBatchMgr.getServiziMgr();
		DbMgr dbMgr = sigitBatchMgr.getDbMgr();
		
		try {
			List<SigitRPfRuoloPaDto> idUtentiPaAttivi = dbMgr.findIdUtentiPAAttivi();
			for (SigitRPfRuoloPaDto idUtente : idUtentiPaAttivi) {
				SigitTPersonaFisicaDto personaFisica = dbMgr.cercaTPersonaFisicaById(ConvertUtil.convertToInteger(idUtente.getIdPersonaFisica()));
				
				try {
					String codiceFiscale = personaFisica.getCodiceFiscale();
					List<SigitTVerificaDto> sveglieVerifiche = dbMgr.findVerificheByCodiceFiscale(codiceFiscale);
					List<SigitTAccertamentoDto> sveglieAccertamento = dbMgr.findAccertamentiByCodiceFiscale(codiceFiscale);
					List<SigitTIspezione2018ByCodiceFiscaleDto> sveglieIspezione2018 = dbMgr.findIspezioni2018ByCodiceFiscale(codiceFiscale);
					List<SigitTSanzioneDto> sveglieSanzioni = dbMgr.findSanzioniByCodiceFiscale(codiceFiscale);
					if (!sveglieVerifiche.isEmpty() || !sveglieAccertamento.isEmpty() || !sveglieIspezione2018.isEmpty() || !sveglieSanzioni.isEmpty()) {
						if (!sigitBatchMgr.invioSvegliaUtenteAttivo(personaFisica.getEmail(), codiceFiscale, sveglieVerifiche, sveglieAccertamento, sveglieIspezione2018, sveglieSanzioni)) {
							throw new Exception("Sveglia di utente "+ codiceFiscale + " non inviata.");
						}
					}
				} catch(Exception e) {
					insertWrkLogMemo(e);
				}
			}
		}
		catch(Exception e ) {
			insertWrkLogMemo(e);
		}
	}	
	
	/**
	 * Invia sveglie agli utenti non attivi
	 * @throws DbManagerException
	 */
	private void inviaSveglieUtentiNonAttivi() throws DbManagerException {
		SigitbatchMgr sigitBatchMgr = getSigitbatchMgr();
		ServiziMgr serviziMgr = sigitBatchMgr.getServiziMgr();
		DbMgr dbMgr = sigitBatchMgr.getDbMgr();
		
		try {
			List<SigitTVerificaByUtentiNonAttiviDto> sveglieVerifiche = dbMgr.findVerificheByUtentiNonAttivi();	
			if (!sveglieVerifiche.isEmpty()) {
				List<String> errors = sigitBatchMgr.invioSveglieUtentiNonAttivi("CIT: MEMO Sveglie VERIFICHE utenti non più attivi", "VERIFICA", sveglieVerifiche);
				insertWrkLogMemo(errors);
			}
		} catch(Exception e ) {
			insertWrkLogMemo(e);
		}
		
		try {
			List<SigitTAccertamentoByUtentiNonAttiviDto> sveglieAccertamento = dbMgr.findAccertamentiByUtentiNonAttivi();	
			if (!sveglieAccertamento.isEmpty()) {
				List<String> errors = sigitBatchMgr.invioSveglieUtentiNonAttivi("CIT: MEMO Sveglie ACCERTAMENTI utenti non più attivi", "ACCERTAMENTO", sveglieAccertamento);
				insertWrkLogMemo(errors);
			}
		} catch(Exception e ) {
			insertWrkLogMemo(e);
		}
		
		try {
			List<SigitTIspezione2018ByUtentiNonAttiviDto> sveglieIspezione2018 = dbMgr.findIspezioni2018ByUtentiNonAttivi();
			if (!sveglieIspezione2018.isEmpty()) {				
				List<String> errors = sigitBatchMgr.invioSveglieUtentiNonAttivi("CIT: MEMO Sveglie ISPEZIONI utenti non più attivi", "ISPEZIONE", sveglieIspezione2018);
				insertWrkLogMemo(errors);
			}
		} catch(Exception e ) {
			insertWrkLogMemo(e);
		}
	
		try {
			List<SigitTSanzioneAccertamentiByUtentiNonAttiviDto> sveglieSanzioniAccertamenti = dbMgr.findSanzioniAccertamentiByUtentiNonAttivi();	
			if (!sveglieSanzioniAccertamenti.isEmpty()) {				
				List<String> errors = sigitBatchMgr.invioSveglieUtentiNonAttivi("CIT: MEMO Sveglie SANZIONI (ACCERTAMENTI) di utenti non più attivi", "SANZIONE", sveglieSanzioniAccertamenti);
				insertWrkLogMemo(errors);
			}
		} catch(Exception e ) {
			insertWrkLogMemo(e);
		}

		try {
			List<SigitTSanzioneIspezione2018ByUtentiNonAttiviDto> sveglieSanzioniIspezioni2018 = dbMgr.findSanzioniIspezione2018ByUtentiNonAttivi();	
			if (!sveglieSanzioniIspezioni2018.isEmpty()) {				
				List<String> errors = sigitBatchMgr.invioSveglieUtentiNonAttivi("CIT: MEMO Sveglie SANZIONI (ISPEZIONI) di utenti non più attivi", "SANZIONE", sveglieSanzioniIspezioni2018);
				insertWrkLogMemo(errors);
			}
		} catch(Exception e ) {
			insertWrkLogMemo(e);
		}	
	}

	
	/**
	 * Invia sveglie ai responsabili di impianti
	 * @throws DbManagerException
	 */
	private void inviaSvegliaResponsabiliImpianti() throws DbManagerException {
		SigitbatchMgr sigitBatchMgr = getSigitbatchMgr();
		ServiziMgr serviziMgr = sigitBatchMgr.getServiziMgr();
		DbMgr dbMgr = sigitBatchMgr.getDbMgr();
		try {
			List<SigitVRicercaAllegatiDto> impiantiConInterventiDaNotificare = dbMgr.findAllegatiInviatiByDaysInterval(30);
			
			for(SigitVRicercaAllegatiDto impianto : impiantiConInterventiDaNotificare) {
				BigDecimal codImpianto = impianto.getCodiceImpianto();
				InputAllegatiComp allegatiComp = new InputAllegatiComp();
				allegatiComp.setCodiceImpianto(ConvertUtil.convertToInteger(codImpianto));
				allegatiComp.setIdAllegato(ConvertUtil.convertToInteger(impianto.getIdAllegato()));
				
				String elencoCompAttivi = sigitBatchMgr.getElencoCompAttivi(allegatiComp, ConvertUtil.convertToString(impianto.getFkTipoDocumento()));
				
				if (StringUtils.isNotEmpty(elencoCompAttivi)) {
					try {
						List<SigitRImpRuoloPfpgDto> responsabili = dbMgr
								.findResponsabileImpiantoAttivoByCodImpianto(ConvertUtil.convertToInteger(codImpianto));
						List<SigitVRicerca3ResponsabileDto> responsabiliTerzi = dbMgr
								.findResponsabile3AttivoByCodImpianto(ConvertUtil.convertToInteger(codImpianto));
						List<String> emailsSveglia = new ArrayList<String>();
						for (SigitRImpRuoloPfpgDto responsabile : responsabili) {
							String email = null;
							BigDecimal responsabileFkPersonaFisica = responsabile.getFkPersonaFisica();
							BigDecimal responsabileFkPersonaGiuridica = responsabile.getFkPersonaGiuridica();

							if (responsabileFkPersonaFisica != null) {
								SigitTPersonaFisicaDto personaFisica = dbMgr.cercaTPersonaFisicaById(
										ConvertUtil.convertToInteger(responsabileFkPersonaFisica));
								email = personaFisica.getEmail();
							} else if (responsabileFkPersonaGiuridica != null) {
								SigitTPersonaGiuridicaDto personaGiuridica = dbMgr.cercaTPersonaGiuridicaById(
										ConvertUtil.convertToInteger(responsabileFkPersonaGiuridica));
								email = personaGiuridica.getEmail();
							}

							emailsSveglia.add(email);
						}

						for (SigitVRicerca3ResponsabileDto responsabileTerzo : responsabiliTerzi) {
							SigitTPersonaGiuridicaDto personaGiuridica = dbMgr.cercaTPersonaGiuridicaById(
									ConvertUtil.convertToInteger(responsabileTerzo.getFkPg3Resp()));
							emailsSveglia.add(personaGiuridica.getEmail());
						}

						List<String> errors = sigitBatchMgr.invioSvegliaInterventiImpianto(emailsSveglia, impianto, elencoCompAttivi);
						insertWrkLogMemo(errors);
					} catch (Exception e) {
						insertWrkLogMemo(e);
					}
				} else {
					SigitTAllegatoDto sigitTAllegatoDto = dbMgr.cercaAllegatoById(impianto.getIdAllegato());
					sigitTAllegatoDto.setDtInvioMemo(ConvertUtil.convertDateToTimestamp(new Date()));
					dbMgr.getSigitTAllegatoDao().update(sigitTAllegatoDto);
				}
			}
			
		} catch(Exception e ) {
			insertWrkLogMemo(e);
		}	
	}
}
