package com.shuga.heroes.client.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.shuga.heroes.client.entity.Hero;
import com.shuga.heroes.client.entity.Sidekick;
import com.shuga.heroes.client.service.ApplicationService;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SpringUI
public class VaadinUI extends UI {
	private static final long serialVersionUID = -6767507126161044128L;

	@Autowired
	private ApplicationService appService;

	final Grid<Hero> grid;

	final TextField txtHeroName = new TextField("Name: ");
	final ComboBox<Boolean> cmbHeroStatus = new ComboBox<Boolean>("Active?");
	final TextField txtSidekickName = new TextField("Name: ");;
	final ComboBox<Boolean> cmbSidekickStatus = new ComboBox<Boolean>("Active?");
	final Window windowModal = new Window("ADD A HEROE & SIDEKICK");

	public VaadinUI() {
		this.grid = new Grid<>(Hero.class);
	}

	public ApplicationService getAppService() {
		return appService;
	}

	public void setAppService(ApplicationService appService) {
		this.appService = appService;
	}

	@Override
	protected void init(VaadinRequest request) {
		Label lblMain = new Label("HEROES' TOUR");
		Button btnAdd = new Button("Add Heroe", e -> getWindowModal());
		
		grid.setColumnOrder("id", "name", "status");
		grid.addColumn("sidekick.name");
		grid.getColumn("sidekick.name").setCaption("Sidekick");
		grid.getColumn("sidekick").setHidden(true);
		grid.getColumn("id").setHidden(true);
		loadGridItems();

		VerticalLayout vlMain = new VerticalLayout();
		vlMain.addComponent(lblMain);
		vlMain.addComponent(btnAdd);
		vlMain.addComponent(grid);
		setContent(vlMain);
	}

	private void getWindowModal() {
		txtHeroName.clear();;
		txtSidekickName.clear();
		cmbHeroStatus.setItems(true, false);
		cmbHeroStatus.setEmptySelectionCaption("Select...");
		cmbSidekickStatus.setItems(true, false);
		cmbSidekickStatus.setEmptySelectionCaption("Select...");
		windowModal.setResizable(false);
		
		Button btnAdd = new Button("Add", e -> saveHero(e));
		
		Panel panel = new Panel("HEROE");
		panel.addStyleName("mypanelexample");
		panel.setSizeUndefined();

		Panel panel2 = new Panel("SIDEKICK");
		panel2.addStyleName("mypanelexample");
		panel2.setSizeUndefined();

		
		FormLayout content1 = new FormLayout();
		content1.addStyleName("mypanelcontent");
		content1.addComponent(txtHeroName);
		content1.addComponent(cmbHeroStatus);
		content1.setSizeUndefined(); 
		content1.setMargin(true);
		
		FormLayout content2 = new FormLayout();
		content2.addStyleName("mypanelcontent");
		content2.addComponent(txtSidekickName);
		content2.addComponent(cmbSidekickStatus);
		content2.setSizeUndefined(); 
		content2.setMargin(true);
		
		panel.setContent(content1);
		panel2.setContent(content2);

		VerticalLayout vlPanel = new VerticalLayout();
		vlPanel.setSizeUndefined();
		vlPanel.addComponent(panel);
		vlPanel.addComponent(panel2);
		vlPanel.addComponent(btnAdd);
		
		windowModal.setContent(vlPanel);
		windowModal.setModal(true);
		windowModal.center();
		windowModal.setEnabled(true);
		
		txtHeroName.focus();
		UI.getCurrent().addWindow(windowModal);
	}

	private void loadGridItems() {
		grid.setItems(getAppService().getAllHeroes());
	}

	private Sidekick saveSidekick() {
		Sidekick sdk = new Sidekick();
		sdk.setName(txtSidekickName.getValue());
		sdk.setStatus(cmbSidekickStatus.getValue());
		return getAppService().createSidekic(sdk).readEntity(Sidekick.class);
	}

	private void saveHero(ClickEvent event) {
		Sidekick sdk = saveSidekick();
		Hero hero = new Hero();
		hero.setName(txtHeroName.getValue());
		hero.setStatus(cmbHeroStatus.getValue());
		hero.setSidekick(sdk);
		getAppService().createHero(hero);
		loadGridItems();
		windowModal.close();
	}

}
