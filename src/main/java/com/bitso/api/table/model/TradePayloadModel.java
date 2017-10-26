package com.bitso.api.table.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bitso.entity.TradePayload;

import javafx.scene.control.TableCell;

public class TradePayloadModel extends TableCell<TradePayload, Object> {

	public TradePayloadModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateItem(Object item, boolean empty) {
		String style = getTableRow() != null && getTableRow().getItem() != null
				&& getTableRow().getItem().getTid().equals("N/A") ? "-fx-text-fill: red" : "-fx-text-fill: green";
		String cellValue = "";
		System.out.println(getTableColumn().getId());
		if (item instanceof String) {
			cellValue = String.valueOf(item);
			try {
				Double itemDouble = Double.parseDouble(cellValue);
				NumberFormat numberFormat = new DecimalFormat("#0.000000");
				cellValue = numberFormat.format(itemDouble);
			} catch (NumberFormatException e) {
			}
		} else if (item instanceof Date) {
			DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss.SSS");
			cellValue = df.format((Date) item);
		} else if (item instanceof Double) {
		}
		setStyle(style);
		setText(cellValue);
	}
}