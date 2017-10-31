package com.bitso.api.table.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.bitso.entity.WebSocketPayload;

import javafx.scene.control.TableCell;

public class WebSocketPayloadModel extends TableCell<WebSocketPayload,Object> {

	@Override
	protected void updateItem(Object item, boolean empty) {
		String style = "-fx-text-fill: blue";
		String cellValue = "";

		if (item instanceof String) {
			cellValue = String.valueOf(item);
			try {
				Double itemDouble = Double.parseDouble(cellValue);
				NumberFormat numberFormat = new DecimalFormat("#0.000000");
				cellValue = numberFormat.format(itemDouble);
			} catch (NumberFormatException e) {
				cellValue=String.valueOf(item);
			}
		} 
		setStyle(style);
		setText(cellValue);
	}

}

