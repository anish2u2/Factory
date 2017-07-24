package org.factory.design.listeners;

import org.adapter.framework.event.contract.Context;
import org.adapter.framework.event.contract.EventListener;

/*
 * @author Anish Singh
 * 
 * This class will listen for adapter framework that initialize this framework.
 * 
 */

public class ListenAdpterFrameworkInitialization implements EventListener {

	private Context context;

	public void onEvent(Context context) {
		this.context = context;
	}

}
