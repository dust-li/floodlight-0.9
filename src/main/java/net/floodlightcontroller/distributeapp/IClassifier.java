package net.floodlightcontroller.distributeapp;

import net.floodlightcontroller.packet.Ethernet;

/**
 *  this is the classifier interface that all classifier should implements
 * @author ljxuan
 *
 */
public interface IClassifier {
	
	/**
	 *  the classifier should be initialized in this function
	 */
	public void classifierInitialize();
	/**
	 *  the main classifier thread should be started in this function.
	 *  this function was called in the module startup process
	 */
	public void classifierStartUp();
	
	/**
	 *  the classifier should return the type
	 */
//	public Future<> doClassify(Ethernet ethernetPacket);
	
}
