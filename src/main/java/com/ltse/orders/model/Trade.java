package com.ltse.orders.model;

import java.util.Date;

/**
 * Represents a Trade in the system
 * 
 * @author Alex De Leon
 */
public class Trade {

	private Date transactionTime;
	private String broker;
	private String sequenceId;
	private String type;
	private String symbol;
	private Integer quantity;
	private Double price;
	private TradeSide side;
	private boolean accepted;
	private String rejectionMessage;

	public Trade() {

	}

	/* 
	 * Getters and Setters
	 */
	
	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public TradeSide getSide() {
		return side;
	}

	public void setSide(TradeSide side) {
		this.side = side;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	public String getRejectionMessage() {
		return rejectionMessage;
	}

	public void setRejectionMessage(String rejectionMessage) {
		this.rejectionMessage = rejectionMessage;
	}

	/*
	 *  Generated HashCode and Equals methods
	 */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accepted ? 1231 : 1237);
		result = prime * result + ((broker == null) ? 0 : broker.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((rejectionMessage == null) ? 0 : rejectionMessage.hashCode());
		result = prime * result + ((sequenceId == null) ? 0 : sequenceId.hashCode());
		result = prime * result + ((side == null) ? 0 : side.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((transactionTime == null) ? 0 : transactionTime.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (accepted != other.accepted)
			return false;
		if (broker == null) {
			if (other.broker != null)
				return false;
		} else if (!broker.equals(other.broker))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (rejectionMessage == null) {
			if (other.rejectionMessage != null)
				return false;
		} else if (!rejectionMessage.equals(other.rejectionMessage))
			return false;
		if (sequenceId == null) {
			if (other.sequenceId != null)
				return false;
		} else if (!sequenceId.equals(other.sequenceId))
			return false;
		if (side != other.side)
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (transactionTime == null) {
			if (other.transactionTime != null)
				return false;
		} else if (!transactionTime.equals(other.transactionTime))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
