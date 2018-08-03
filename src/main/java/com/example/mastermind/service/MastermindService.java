package com.example.mastermind.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.mastermind.utils.Utilitaires;

@Service
public class MastermindService {
	public static Logger logger = Logger.getLogger(MastermindService.class.getName());

	private MastermindService() {
	}

	/**
	 * 
	 * @throws IOException
	 */
	public static void execute() throws IOException {
		logger.log(Level.INFO, null, "DEBUT : " + new Timestamp(new Date().getTime()));
		Map<String, Object> mapReponseStart = Utilitaires.sendWithMsgBody(Utilitaires.URL_START,
				Utilitaires.METHOD_POST,
				Utilitaires.buildMsg(Utilitaires.TOKEN_VALUE, Utilitaires.NAME_VALUE, Utilitaires.NAME));
		Integer sizeValue = null != mapReponseStart.get(Utilitaires.SIZE)
				? (Integer) mapReponseStart.get(Utilitaires.SIZE)
				: 8;
		List<Integer> valuesGuess = filterValues(sizeValue);
		String goodVal = "";
		String result = "";
		String[] tab = new String[sizeValue];
		for (Integer k = 0; k < sizeValue; k++) {
			tab[k] = "_";
		}
		
		Collections.sort(valuesGuess);
		for (Integer j = 0; j < sizeValue; j++) {
			int i = 0;
			String oldCrtGuessed = "";
			String newCrtGuess = "";
			while (!valuesGuess.isEmpty()) {
				newCrtGuess = valuesGuess.get(i).toString();
				if(!oldCrtGuessed.equals(newCrtGuess)) {
					tab[j] = valuesGuess.get(i).toString();
					result = Utilitaires.tabToString(tab);
					Map<String, Object> mapReponse = Utilitaires.sendWithMsgBody(Utilitaires.URL_TEST,
							Utilitaires.METHOD_POST,
							Utilitaires.buildMsg(Utilitaires.TOKEN_VALUE, result, Utilitaires.RESULT));
					Integer val = null != mapReponse ? (Integer) mapReponse.get(Utilitaires.GOOD) : 0;
					goodVal = val.toString();
					Integer index = j + 1;
					if (goodVal.equals(index.toString())) {
						valuesGuess.remove(i);
						break;
					}
					oldCrtGuessed = newCrtGuess;
				}
				i++;
			}
			if (goodVal.equalsIgnoreCase(sizeValue.toString())) {
				break;
			}
		}
		logger.log(Level.INFO, null, "Le code secret est : " + result);
		logger.log(Level.INFO, null, "FIN " + new Timestamp(new Date().getTime()));

	}

	/**
	 * 
	 * @param sizeValue
	 * @return
	 * @throws IOException
	 */
	private static List<Integer> filterValues(Integer sizeValue) throws IOException {
		List<Integer> valuesGuess = new ArrayList<>();
		String chaine = "";
		for (int i = 0; i < 10; i++) {
			chaine = Utilitaires.padding("", sizeValue.intValue(), i);
			Map<String, Object> mapReponse = Utilitaires.sendWithMsgBody(Utilitaires.URL_TEST, Utilitaires.METHOD_POST,
					Utilitaires.buildMsg(Utilitaires.TOKEN_VALUE, chaine, Utilitaires.RESULT));
			Integer good = null != mapReponse ? (Integer) mapReponse.get(Utilitaires.GOOD) : 0;
			if (good > 0) {
				for (int m = 0; m < good; m++) {
					valuesGuess.add(i);
				}
			}
			if (sizeValue.equals(valuesGuess.size())) {
				break;
			}
		}
		return valuesGuess;
	}

}
