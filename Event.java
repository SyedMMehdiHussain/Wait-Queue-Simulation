public class Event implements Comparable<Event>{

	static	int instanceCounter=1;
	private int userID;
	Eventtype type;
	private double arrivaltime;
	private double duration;
	private double totalTime= arrivaltime + duration;

	public Event(int iD, Eventtype type,double time) {
		super();
		
		userID=instanceCounter++;
		this.type= type;
		this.totalTime = time; 
	}
	
	public void setEvent(Eventtype type,double time) {
		
		this.type= type;
		this.totalTime = time; 
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return userID;
	}

	/**
	 * @return the start_time
	 */
	public double getStart_time() {
		return arrivaltime;
	}


	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(double start_time) {
		this.arrivaltime = start_time;
	}


	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}


	/**
	 * @param duration the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}


	/**
	 * @return the type
	 */
	public Eventtype getType() {
		return type;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "event [ID=" + userID + ", type=" + type + ", time=" + totalTime + "]";
	}


	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		userID = iD;
	}

	/**
	 * @return the type
	 */
	public Eventtype isType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Eventtype type) {
		this.type = type;
	}

	/**
	 * @return the time
	 */
	public double getTime() {
		return totalTime;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(double time) {
		this.totalTime = time;
	}

	public Event() {
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		else if (getClass() != other.getClass())
			return false;
		else {
			Event otherPair = (Event) other;
			return totalTime==(otherPair.totalTime) ;
		}
	}

	@Override
	public int compareTo(Event o) {

		if (this.totalTime > (o.totalTime))
		{
		
			return 1;
		}
		else if (this.totalTime==o.totalTime)
			return 0;
		else return -1;
	
	}

}