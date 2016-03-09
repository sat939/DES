import java.math.BigInteger;
import java.util.*;
public class Message {
	static final int pc1[] = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34,
        26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63,
        55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53,
        45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };
    static final int pc2[] = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23,
        19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30,
        40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };

    static int LS[]={ 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
    static final int InPerm[] = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36,
        28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32,
        24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19,
        11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
    static final int Exp[] = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11,
        12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22,
        23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };
    static final int Sbox[][][] = {
        {
                     { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 }, 
                     { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                     { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                     { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
        {
                     { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 }, 
                     { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                     { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                     { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
        {
                     { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                     { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                     { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                     { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
        {
                     { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 }, 
                     { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                     { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                     { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
        {
                     { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 }, 
                     { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                     { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                     { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
        {
                     { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                     { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                     { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                     { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
        {
                     { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 }, 
                     { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                     { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                     { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
        {
                     { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                     { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                     { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                     { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };
    static final int P[] = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5,
        18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4,
        25 };
    static final int Invperm[] = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15,
        55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53,
        21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19,
        59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 
25 };







public static void main(String[] args)
{
	Scanner sc= new Scanner(System.in);
	System.out.println("enter the message in hexa decimal formatt");
	String InputMessage= sc.nextLine();
	String m= convertMsgtoBin(InputMessage);
	System.out.println(m);
	Scanner ms= new Scanner(System.in);
	System.out.println("enter the hexadecimal key");
	String Ikey= ms.nextLine();
	String k= convertMsgtoBin(Ikey);
	String Lkey[]= new String[30];
	String Rkey[]= new String[30];
	String Key[]= new String[60];
	Key[0]="";
	Lkey[0]="";
	Rkey[0]="";
	String Permkey[]= new String[60];
	Arrays.fill(Permkey,"");
	String Msg[]= new String[64];
	String Lmsg[]= new String[64];
	String Rmsg[]= new String[32];
	String Emsg[]= new String[32];
	Msg[0]="";
	Lmsg[0]="";
	Rmsg[0]="";
	String Ep[]= new String[50];
	Ep[0]="";
	String Pmsg[]= new String[60];
	Pmsg[0]="";
	String Text="";
	String func= new String();
	func="";
	String Dmsg[]= new String[Invperm.length];
	Arrays.fill(Dmsg,"");
	String RL=new String();
	RL="";
	String Leftkey[]=new String[64];
	String Rightkey[]= new String[64];
	Arrays.fill(Leftkey,"");
	Arrays.fill(Rightkey,"");
	String temp="";
	String DecMsg[]=new String[64];
	Arrays.fill(DecMsg,"");
	String Decryptmsg="";
	for(int i=0;i<pc1.length;i++){
		int j= pc1[i];
		Key[0]= Key[0]+k.charAt(j-1);
	}
	
	for( int i=0;i<Key[0].length();i++){
		if(i<Key[0].length()/2)
			Lkey[0]+=Key[0].charAt(i);
		else
			Rkey[0]+=Key[0].charAt(i);
	}
	for(int i=1;i<=16;i++)
	{
		Lkey[i]= LeftShift(Lkey[i-1],LS[i-1]);
		Rkey[i]= LeftShift(Rkey[i-1],LS[i-1]);
		Key[i]= Lkey[i]+Rkey[i];
		//System.out.println(Key[i]);
	}
	for(int i=1;i<=16;i++){
		for(int j=0;j<pc2.length;j++){
			int p=pc2[j];
			
		Permkey[i]= Permkey[i]+Key[i].charAt(p-1);
		}
		//System.out.println(Permkey[i]);
	}
	for(int i=0;i<InPerm.length;i++){
		int j=InPerm[i];
		Msg[0]=Msg[0]+m.charAt(j-1);
		
	}
	//System.out.println(Msg[0]);
	for( int i=0;i<Msg[0].length();i++){
		if(i<Msg[0].length()/2)
			Lmsg[0]+=Msg[0].charAt(i);
		else
			Rmsg[0]+=Msg[0].charAt(i);
	}
	//System.out.println(Lmsg[0]+"\n"+Rmsg[0]);
	//for(int n=0;n<=16;n++)
		//System.out.println(Permkey[n]);
	for(int n=1;n<=16;n++)
	{
		Ep[0]=""; //expansion permutation
		Lmsg[n]=Rmsg[n-1];
		func= function(Rmsg[n-1],Permkey[n]);
		Rmsg[n]=Xor(Lmsg[n-1],func);
	}
		String str=Rmsg[16]+Lmsg[16];
		for(int i=0;i<Invperm.length;i++){
		int j= Invperm[i];
		Text+=str.charAt(j-1);
		}
		//System.out.println("the ciphe text is:"+Text);
		System.out.println("Hexadecimal Output: "+toHex(Text));
		//System.out.println(Text);
		
		
		//decryption
		for(int i=0;i<Invperm.length;i++){
			int j=Invperm[i];
			//System.out.println(Text.charAt(i));
			Dmsg[j-1]= ""+Text.charAt(i);
			
		}
		for(int i=0; i<Dmsg.length;i++)
			RL+= Dmsg[i];
		//System.out.println("dmsg"+RL);
		for( int i=0;i<RL.length();i++){
			if(i<RL.length()/2)
				Rightkey[16]+=RL.charAt(i);
			else
				Leftkey[16]+=RL.charAt(i);
		}
		//System.out.println(Leftkey[16]+" "+Rightkey[16]);
		for(int i=15;i>=0;i--){
			Rightkey[i]=Leftkey[i+1];
			Leftkey[i]=Xor(Rightkey[i+1], function(Leftkey[i+1], Permkey[i+1]));
		}
		//System.out.println(Leftkey[0]);
		temp=Leftkey[0]+Rightkey[0];
		for(int i=0;i<InPerm.length;i++){
			int j=InPerm[i];
			DecMsg[j-1]= ""+temp.charAt(i);
			}
		for(int i=0;i<DecMsg.length;i++){
			Decryptmsg+=DecMsg[i];
		}
		//System.out.println("Message after decryption is "+Decryptmsg);
		System.out.println("Decrypted message is"+toHex(Decryptmsg));
}
		


public static String toHex(String s){
	String s1,hex="";
	int dec,j;
	for(int i=0;i<s.length();)
	{
		j=i+4;
		s1=s.substring(i,j);
		dec=Integer.parseInt(s1,2);
		switch(dec)
		{
			case 10:hex+="A";break;
			case 11:hex+="B";break;
			case 12:hex+="C";break;
			case 13:hex+="D";break;
			case 14:hex+="E";break;
			case 15:hex+="F";break;
			default:hex+=dec;break;
		}
		i=i+4;
	}
	return hex;
}


public static String convertMsgtoBin(String msg)
{
	String BinMsg= new BigInteger(msg, 16).toString(2);
	while((msg.length()*4)!=BinMsg.length())
		BinMsg="0"+BinMsg;
	return BinMsg;
}
public static String LeftShift(String s,int ls){
	String left=(s.substring(0, ls));
	String st=s.substring(ls,s.length())+left;
	return st;
	
}
public static String Xor(String s,String k){
	String value= new String();
	value="";
	for(int i=0;i<s.length();i++){
		if(s.charAt(i)==k.charAt(i))
			value +="0";
		else
			value+= "1";
		
	}
	return value;
	
}
public static int ConvBinToDec(int a){

    int decimal = 0;
    int pwr = 0;
    while (true) {
           if (a == 0) {
                 break;
           } else {
                 int temp = a % 10;
                 decimal += temp * Math.pow(2, pwr);
                 a = a / 10;
                 pwr++;
           }
    }
    return decimal;

}
public static String RedtoFour(String s)
{
	
	
	for(int i=s.length();i<4;i++){
	s="0"+s;	
	}
	return s;
}
public static String function(String s,String k){
	String Ep[]= new String[64];
	String row= new String();
	String column= new String();
	String Pmsg= new String();
	Pmsg="";
	Ep[0]="";
	for(int i=0;i<Exp.length;i++){
		int j= Exp[i];
		Ep[0]+=s.charAt(j-1);	
	}
	//System.out.println("ep"+Ep[0]);
	
	String R= Xor(Ep[0],k);
	//System.out.println("R: "+R);
	String B[]= new String[50]; //K+E(Ro)
	String SB= new String(); //after S box
	SB="";
	for(int i=0;i<R.length()/6;i++){
		B[i]=R.substring(i*6,(i+1)*6);
		row= B[i].charAt(0)+""+B[i].charAt(B[i].length()-1);
		column= B[i].substring(1,5);
        int sbox = Sbox[i][ConvBinToDec(new Integer(row))][ConvBinToDec(new Integer(
                column))];
        String Bin= Integer.toBinaryString(sbox);
        SB=SB+RedtoFour(Bin);
	}
	for( int i=0;i<P.length;i++){
		int j=P[i];
		Pmsg+=SB.charAt(j-1);
	}
	//System.out.println(Pmsg[0]);

	return Pmsg;
	
}
}
