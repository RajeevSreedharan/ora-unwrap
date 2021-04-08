/*
 * Copyright 2017 Rajeev Sreedharan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package in.net.rajeev.oraunwrap.ui.commands;

import java.io.File;
import java.util.Observable;

/**
 * @author Rajeev Sreedharan
 *
 */
public class OpenActionObservable extends Observable {
	protected static OpenActionObservable openActionObservable = null;
	File selectedFile = null;

	private OpenActionObservable() {
		
	}

	public static OpenActionObservable getInstance() {
		if (openActionObservable == null)
			openActionObservable = new OpenActionObservable();
		return openActionObservable;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
		setChanged();
		notifyObservers();
	}

	public File getSelectedFile() {
		return selectedFile;
	}

}
