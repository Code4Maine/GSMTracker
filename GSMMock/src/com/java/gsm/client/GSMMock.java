package com.java.gsm.client;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.client.ClientProtocolException;

public class GSMMock
{
    public GSMMock()
    {
        super();
    }
    
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                InputStream in = serialPort.getInputStream();
                //OutputStream out = serialPort.getOutputStream();
                
                (new Thread(new SerialReader(in))).start();
                //(new Thread(new SerialWriter(out))).start();

            }
            else
            {
                System.out.println("Error: Unable to start serila port.");
            }
        }     
    }
    
    /** */
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            String result = new String();
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                	String temp = new String(buffer,0,len);
                    if (temp.contains("\n"))
                    {
                    	System.out.println(result);
                    	String [] items = result.split(",");
                    	System.out.println("lat : "+items[0]);
                    	System.out.println("long : "+items[1]);
                    	
                    	SendHttpPost sendPost = new SendHttpPost();
                    	
                    	sendPost.sendRequest(items[0], items[1]);
                    	result = null;
                    }else{
                    	if (temp != null)
                    	{ 
                    		if(result != null){
                    			result = result + temp;
                    		}else{
                    			result = temp;
                    		}
                    	}
                    		
                    }
                }
                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
    
    public static void main ( String[] args ) throws ClientProtocolException, IOException
    {
        try
        {
            (new GSMMock()).connect(args[0]);
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}