/**
 * <a href="http://www.openolat.org">
 * OpenOLAT - Online Learning and Training</a><br>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); <br>
 * you may not use this file except in compliance with the License.<br>
 * You may obtain a copy of the License at the
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache homepage</a>
 * <p>
 * Unless required by applicable law or agreed to in writing,<br>
 * software distributed under the License is distributed on an "AS IS" BASIS, <br>
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
 * See the License for the specific language governing permissions and <br>
 * limitations under the License.
 * <p>
 * Initial code contributed and copyrighted by<br>
 * frentix GmbH, http://www.frentix.com
 * <p>
 */
package org.olat.modules.forms.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.olat.core.CoreSpringFactory;
import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.Component;
import org.olat.core.gui.components.form.flexible.impl.Form;
import org.olat.core.gui.control.Controller;
import org.olat.core.gui.control.WindowControl;
import org.olat.modules.forms.EvaluationFormsModule;
import org.olat.modules.forms.model.xml.FileUpload;
import org.olat.modules.forms.ui.FileUploadCompareController;
import org.olat.modules.forms.ui.FileUploadController;
import org.olat.modules.forms.ui.FileUploadEditorController;
import org.olat.modules.forms.ui.model.CompareResponse;
import org.olat.modules.forms.ui.model.EvaluationFormExecutionElement;
import org.olat.modules.forms.ui.model.EvaluationFormResponseController;
import org.olat.modules.forms.ui.model.EvaluationFormResponseControllerElement;
import org.olat.modules.portfolio.ui.editor.PageElement;
import org.olat.modules.portfolio.ui.editor.PageElementEditorController;
import org.olat.modules.portfolio.ui.editor.PageElementRenderingHints;
import org.olat.modules.portfolio.ui.editor.PageRunControllerElement;
import org.olat.modules.portfolio.ui.editor.PageRunElement;
import org.olat.modules.portfolio.ui.editor.SimpleAddPageElementHandler;

/**
 * 
 * Initial date: 02.02.2018<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class FileUploadHandler implements EvaluationFormElementHandler, SimpleAddPageElementHandler {

	private final boolean restrictedEdit;
	
	public FileUploadHandler(boolean restrictedEdit) {
		this.restrictedEdit = restrictedEdit;
	}
	
	@Override
	public String getType() {
		return "formfileupload";
	}

	@Override
	public String getIconCssClass() {
		return "o_icon_fileupload";
	}

	@Override
	public PageRunElement getContent(UserRequest ureq, WindowControl wControl, PageElement element, PageElementRenderingHints hints) {
		if(element instanceof FileUpload) {
			FileUpload fileUpload = (FileUpload) element;
			Controller ctrl = new FileUploadController(ureq, wControl, fileUpload);
			return new PageRunControllerElement(ctrl);
		}
		return null;
	}

	@Override
	public PageElementEditorController getEditor(UserRequest ureq, WindowControl wControl, PageElement element) {
		if(element instanceof FileUpload) {
			FileUpload fileUpload = (FileUpload) element;
			return new FileUploadEditorController(ureq, wControl, fileUpload, restrictedEdit);
		}
		return null;
	}

	@Override
	public PageElement createPageElement(Locale locale) {
		EvaluationFormsModule evaluationFormModule = CoreSpringFactory.getImpl(EvaluationFormsModule.class);
		FileUpload part = new FileUpload();
		part.setId(UUID.randomUUID().toString());
		part.setMaxUploadSizeKB(evaluationFormModule.getMaxFileUploadLimitKB());
		return part;
	}

	@Override
	public EvaluationFormExecutionElement getExecutionElement(UserRequest ureq, WindowControl wControl, Form rootForm,
			PageElement element) {
		if (element instanceof FileUpload) {
			FileUpload fileUpload = (FileUpload) element;
			EvaluationFormResponseController ctrl = new FileUploadController(ureq, wControl, fileUpload, rootForm);
			return new EvaluationFormResponseControllerElement(ctrl);
		}
		return null;
 	}

	@Override
	public List<String> getCompareResponseIdentifiers(PageElement element) {
		if (element instanceof FileUpload) {
			FileUpload fileUpload = (FileUpload) element;
			return Arrays.asList(fileUpload.getId());
		}
		return Collections.emptyList();
	}

	@Override
	public Component getCompareComponent(UserRequest ureq, WindowControl windowControl, PageElement element,
			List<CompareResponse> compareResponses) {
		if(element instanceof FileUpload) {
			FileUpload fileUpload = (FileUpload) element;
			Controller ctrl = new FileUploadCompareController(ureq, windowControl, fileUpload, compareResponses);
			return ctrl.getInitialComponent();
		}
		return null;
	}

}
