package OnionFindFriend;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;


public class ChessFrame extends JFrame{
	
	///游戏界面参数
	private Container contentPane;
	private JPanel upPanel;
	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JPanel downPanel;
	private JComboBox level;          //游戏级别
	private JComboBox size;          //棋盘大小
	private JRadioButton cb1;         //单选按钮，玩家先手
	private JRadioButton cb2;         //单选按钮，电脑先手
	private JButton b4;               //开始
	private JButton b5;               //结束
	private JButton b6;               //退出按钮
	private JButton b[][];          //棋盘按钮
	private JLabel b1;                //欢迎词
	private JLabel b11;  
	private JLabel b12;  
	private JLabel b2;                 //棋局状况
	private JLabel b3;                  //棋局结果
	private JLabel b31; 
	private JLabel b32; 
	
	/////程序参数
	private int startturn;       //先下棋的一方，1代表玩家，2代表电脑
	private int  a[][];          //棋盘信息，0代表无棋子，1代表玩家下的棋子，2代表电脑下的棋子
	private int result;      //棋局结果，1代表玩家赢，2代表电脑赢，3代表平手
	private int c_size;      //棋盘大小，本游戏中有3乘3，4乘四，5乘5三种棋盘
	private int c_level;      //游戏级别，低级，中级，高级分别对应0，1，2
	private int x;          //玩家出的棋子所在位置的行号
	private int y;           //玩家所出棋子所在的列号
	
	public ChessFrame(){
		 
		
		super("井字棋人机作战");
		//调用界面初始化函数
		frameInitnew();
		
		//退出程序
		
		WindowListener w=new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				ChessFrame.this.setVisible(false);
				//ChessFrame.this.dispose();
				//this.setVisibla(false);
				//System.exit(0);
				}
			
		};
		
		this.addWindowListener(w);
		
		//定义响应鼠标单击的事件
		
		ActionListener ch=new ActionListener(){
			public void actionPerformed(ActionEvent e){
			
					c_level=1;
					
			
					c_size=4;
		  
		            	b[0][3].setVisible(true);
    					b[1][3].setVisible(true);
    					b[2][3].setVisible(true);
    					b[3][3].setVisible(true);
    					b[3][0].setVisible(true);
    					b[3][1].setVisible(true);
    					b[3][2].setVisible(true);
    					b[0][4].setVisible(false);
    					b[1][4].setVisible(false);
    					b[2][4].setVisible(false);
    					b[3][4].setVisible(false);
    					b[4][4].setVisible(false);
    					b[4][0].setVisible(false);
    					b[4][1].setVisible(false);
    					b[4][2].setVisible(false);
    					b[4][3].setVisible(false);
		      
					startturn=2;
		
				if(e.getSource()==b4){         //开始按钮
					//调用内部函数，初始化棋盘
				    beginChess();
    				
				}
				else if(e.getSource()==b5){         //结束按钮
					endChess();
				}
				else if(e.getSource()==b6){       //exit
					ChessFrame.this.setVisible(false);
					//System.exit(0);
				}
				else{
					 for(int ii=0;ii<c_size;ii++){
					 	for(int jj=0;jj<c_size;jj++){
					 		if(e.getSource()==b[ii][jj]){
					 		
					 			x=ii; y=jj;
					 		
					 			playchess();
					 		}
					 
					 }
					}
				}//end else
				
			}//end actionPerformed
		};//end actionlistener
		
		
		//将所有按钮加入监听器
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				b[i][j].addActionListener(ch);
			}
		}
		
		b4.addActionListener(ch);
		b5.addActionListener(ch);
		b6.addActionListener(ch);
		
		this.setVisible(true);
		
    }//end Chessframe
    
    
    
    ///界面初始化函数
    private void frameInitnew(){
    	
    	
		contentPane=this.getContentPane();
		//界面的大小和位置
		this.setSize(500,500);
		this.setResizable(false);
		Dimension frameSize=this.getSize();
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height)/2);
    	
    	
    	
    	//将整个界面划分成五个区域，东南西北中
		//西为棋盘
		//北为欢迎词
		//中为空白区
		//东为命令按钮及棋局状况
		//南为游戏结果
		
		//初始状态为3乘3棋盘，级别为低级，电脑先手
		
		c_size=4; 
		startturn=2;
		c_level=1;
		
		
		chessboardFormed();              //形成棋盘
		
	    //其他是固定的，所以不需单独形成函数
		
		//北区，欢迎词
		b1=new JLabel("    来战三百回合！！！",JLabel.CENTER);
		b1.setFont(new java.awt.Font("Times",Font.PLAIN, 25));
		b1.setForeground(Color.black);
		b11=new JLabel("             ",JLabel.CENTER);
		b12=new JLabel("             ",JLabel.CENTER);
		upPanel=new JPanel();
		upPanel.setLayout(new GridLayout(3,1));
		upPanel.setPreferredSize(new Dimension(500, 80));
		upPanel.add(b11);
		upPanel.add(b1);
		upPanel.add(b12);
		contentPane.add(upPanel,BorderLayout.NORTH);
		
		//南区，棋局结果，初始状态为空
		b3=new JLabel(" *************************  ",JLabel.CENTER);
		b31=new JLabel("             ",JLabel.CENTER);
		b32=new JLabel("             ",JLabel.CENTER);
		b3.setFont(new java.awt.Font("Times",Font.PLAIN, 18));
		b3.setForeground(Color.black);
		downPanel=new JPanel();
		downPanel.setPreferredSize(new Dimension(500, 60));
		downPanel.setLayout(new GridLayout(3,1));
		downPanel.add(b31);
		downPanel.add(b3);
		downPanel.add(b32);
		contentPane.add(downPanel,BorderLayout.SOUTH);
		
		
		///东区，包括游戏级别选择，棋盘大小选择，先手方选择，以及开始，结束退出按钮
		rightPanel=new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 360));
		rightPanel.setLayout(new GridLayout(8,1,0,15));
		b2=new JLabel("                    ",JLabel.CENTER);
		b2.setFont(new java.awt.Font("Dialog", 0, 15));
		b4=new JButton("开始");
		b4.setFont(new java.awt.Font("Dialog", 0, 20));
		b4.setBorder(BorderFactory.createRaisedBevelBorder());
		b5=new JButton("结束");
		b5.setFont(new java.awt.Font("Dialog", 0, 20));
		b5.setBorder(BorderFactory.createRaisedBevelBorder());
		b6=new JButton("退出");
		b6.setFont(new java.awt.Font("Dialog", 0, 20));
		b6.setBorder(BorderFactory.createRaisedBevelBorder());
		b5.setEnabled(false);
		b6.setEnabled(false);
	
		rightPanel.add(b2);
		rightPanel.add(b4);
		rightPanel.add(b5);
		rightPanel.add(b6);
		contentPane.add(rightPanel,BorderLayout.EAST);		
		
    }//end  frameInitnew
    
    private void chessboardFormed()     //形成棋盘
    {
    	int i,j;
    	
		leftPanel=new JPanel();
		leftPanel.setPreferredSize(new Dimension(300, 300));
		
		
    	b=new JButton[5][5];
    	leftPanel.setLayout(new GridLayout(5,5,10,10));
    	for(i=0;i<5;i++){
    		for(j=0;j<5;j++){
    			b[i][j]=new JButton();
    			b[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
    			b[i][j].setPreferredSize(new Dimension(30, 30));
    			b[i][j].setBackground(Color.white);
    			b[i][j].setEnabled(false);
    			leftPanel.add(b[i][j]);
    		}
    	}

    		b[0][4].setVisible(false);
    		b[1][4].setVisible(false);
    		b[2][4].setVisible(false);
    		b[3][4].setVisible(false);
    		b[4][4].setVisible(false);
    		b[4][0].setVisible(false);
    		b[4][1].setVisible(false);
    		b[4][2].setVisible(false);
    		b[4][3].setVisible(false);
   
    		
    	leftPanel.setLocation(200,200);
    	contentPane.add(leftPanel,BorderLayout.WEST);
    	
    	
    }//end chessboardFormed()
    
    
    private void beginChess(){
    	int i,j,k;
    	k=c_size/2;
    	a=new int[c_size][c_size];
    	for(i=0;i<c_size;i++){
    		for(j=0;j<c_size;j++){
    			b[i][j].setBackground(Color.white);
    			
    			a[i][j]=0;
    		}
    	}
    	
    
    		a[k][k]=2;
    		b[k][k].setEnabled(false);
    		b[k][k].setBackground(Color.blue);
 
    //	else                //玩家先手，则初始化为空棋盘
    	//     b[k][k].setEnabled(true);
    	for(i=0;i<4;i++){
    		for(j=0;j<c_size;j++){
    			if(i!=k||j!=k){
    				b[i][j].setEnabled(true);
    			}
    			
    		}
    	}
    	
    	
    	
    	b2.setText("玩家思考中...");
    	b3.setText("           ");
    	b4.setEnabled(false);
    	b5.setEnabled(true);
    	b6.setEnabled(false);
    	  	
    }///end beginChess
    
    private void endChess(){   //终止当前游戏，棋盘为空棋盘并且不可用
    	int i,j;
    	for(i=0;i<c_size;i++){
    		for(j=0;j<c_size;j++){
    			b[i][j].setEnabled(false);
				b[i][j].setBackground(Color.white);
    		}
    	}
    	
    	
    	b4.setEnabled(true);
		b5.setEnabled(false);
		b2.setText("           ");
    }//end endChess
    
    private void playchess(){
    	int i,j,k,k1,k2;
    	k=0;
    	for(i=0;i<4;i++){
			for(j=0;j<c_size;j++){
				b[i][j].setEnabled(false);
			}//endfor
		}
		b[x][y].setBackground(Color.red);
		a[x][y]=1;
		b2.setText("电脑思考中...");
		
		
		if(isWinornot()){      //判断是否已经分出胜负
			b4.setEnabled(true);
	 		b5.setEnabled(false);
			//根据result的值判断棋局的结果
			b2.setText("游戏结束");
			switch(result){
				case 1: 
					b3.setText("好吧，竟然被你侥幸赢了，我随你走就是");
					b6.setEnabled(true);
					break;
					
				case 2: 
					b3.setText("真笨，你输了，不赢我你可是离开不了的哦");
					b6.setEnabled(false);
					break;
				case 3: 
					b3.setText("哈哈，平手，算了，看在你诚心诚意求我的份上，我随你走就是");
					b6.setEnabled(true);
					break;
			}
			
		}//endif
		
		else{      
			
			k=choosenext1();
								
			}
			k1=k/10;k2=k%10;   //分离出行号和列号
			b[k1][k2].setBackground(Color.blue);
			a[k1][k2]=2;
			
			if(isWinornot()){
				b4.setEnabled(true);
	 			b5.setEnabled(false);
			    //根据result的值判断棋局的结果
				b2.setText("游戏结束");
				switch(result){
					case 1: 
						b3.setText("好吧，竟然被你侥幸赢了，我随你走就是");
						b6.setEnabled(true);
						break;
					case 2: 
						b3.setText("真笨，你输了，不赢我你可是离开不了的哦");
						b6.setEnabled(false);
						break;
					case 3: 
						b3.setText("哈哈，平手，算了，看在你诚心诚意求我的份上，我随你走就是");
						b6.setEnabled(true);
						break;
				}
				
			}
			else{   //继续接受用户输入
				 	for(i=0;i<4;i++){
					for(j=0;j<4;j++){
						if(a[i][j]==0){
							b[i][j].setEnabled(true);
				}
						}
					}
				b2.setText("玩家思考中...");}
			}
			
		//endelse

   
    
    private boolean isWinornot(){
    	int i,j,k;
    	boolean fa;
    	//找是否在同一行上有连成一条线的
    	for(i=0;i<c_size;i++){
    		k=a[i][0];
    		fa=true;
    		if(k!=0){
    			for(j=1;j<c_size;j++){
    			  if(a[i][j]!=k)
    				fa=false;
    			}
    		   if(fa)  //有一方的棋子在横向上连成一条线了
    		   {
    			   if(k==1) result=1;    //判断是哪一方的棋子
    			   else result=2;
    			   return true;
    		   }
    		}
    		
    	}//endfor
    	
    	//找同一列上有没有连成一条线的
    	for(j=0;j<4;j++){
    		k=a[0][j];
    		fa=true;
    		if(k!=0){
    			for(i=1;i<4;i++){
    			   if(a[i][j]!=k)
    				  fa=false;
    			}
    		    if(fa)  //有一方的棋子在横向上连成一条线了
    		    {
    				if(k==1) result=1;
    				else result=2;
    				return true;
    			}
    		}    		
    	}
    	
    	//找斜对角线有没有连成一条线的
    	k=a[0][0];
    	fa=true;
    	if(k!=0){
    	   	for(i=1;i<4;i++){
    			if(a[i][i]!=k)  fa=false;
    	 	}
    	 	if(fa){
    			if(k==1)  result=1;
    			else result=2;
    			return true;
    		}
    	}
    	
    	j=3;
    	k=a[0][j];
    	fa=true;
    	if(k!=0){
    		for(i=1,j--;i<4;i++,j--){
    			if(a[i][j]!=k) fa=false;
    		}
    		if(fa){
    		if(k==1)  result=1;
    		else result=2;
    		return true;
    	    }
    	}
    	
    	
    	//若经过上面的查找，没有一方获胜，则查找是否还能输入棋子
    	//若还可输入，则返回false，游戏继续
    	//若不能继续输入棋子，则游戏结束，双方达成平手
    	for(i=0;i<4;i++){
    		for(j=0;j<4;j++){
    			if(a[i][j]==0)  return false ; 
    		}
    	}
    	
    	result=3;
    	return true;
    }
    /*
    
   
    //用阿尔发-贝他算法，向前看两部
    */
    private int choosenext1(){
    	// max_i代表最佳走法所在的行号，max_j代表最佳走法所在的列号
		//h为各种状态下的价值评估函数
        int i,j,max_i,max_j,h;
        int p,q;
        int minus,plus;
        minus=200;
        max_i=0;
        max_j=0;
        for(i=0;i<c_size;i++){          //选择第一个空的棋格
        	for(j=0;j<c_size;j++){
        		if(a[i][j]==0){
        			max_i=i;
        			max_j=j;
        			a[i][j]=2;
        			
        			if(isWinornot())   //如果这一步可以导致电脑赢，那么就返回这一步
        			{
        				if(result==2){
        					a[i][j]=0;
        					return 10*max_i+max_j;
        				}
        				
        			}
        			i=c_size;      ////为了跳出循环
        			j=c_size;
        		}
        	}
        } /////找到第一步
        
        
        ///从第一个分支中找出价值函数最小的
        
        for(i=0;i<c_size;i++){          //试探出所有的分支分别求他们的评估函数
        	for(j=0;j<c_size;j++){
        		//求当前棋局的价值评估函数
        		if(a[i][j]==0){
        			a[i][j]=1;
        			h=evaluate();
        			if(h<minus){
        				minus=h;
        			}
        			a[i][j]=0;
        		}//一个有效分支
        		
        	}
        }
        
        plus=minus;
        a[max_i][max_j]=0;
        i=max_i;
        //再查找其他的分支
		//因为第一个找到的棋格无需再判断，将剩余的棋格分成两部分
		//第max_i行第max_j个棋格之后的
		//第max_i行之后的棋格
        for(j=max_j+1;j<c_size;j++){  
        	if(a[i][j]==0){
        			minus=200;
        			a[i][j]=2;
        			if(isWinornot()){
        				if(result==2){
        					a[i][j]=0;
        					return 10*i+j;
        				}
        			}
        			for(p=0;p<c_size;p++){
        				for(q=0;q<c_size;q++){
        					if(a[p][q]==0){
        						a[p][q]=1;
        						h=evaluate();
        						a[p][q]=0;
        						if(h<=plus){   //剪枝，剩下子节点无需再判断
        							minus=h;
        							q=c_size;
        							p=c_size;   //跳出两重循环
        						}
        						else{
        							if(h<minus)  minus=h;
        							
        						}
        						
        					}
        				}
        			}//二层节点
        			if(minus>plus&&minus!=200){   ////plus值增加，更好的走法
        				plus=minus;
        				max_i=i;
        				max_j=j;
        			}
        			a[i][j]=0;
        		}//endif
        }
        for(i++;i<c_size;i++){
        	for(j=0;j<c_size;j++){
        		if(a[i][j]==0){
        			minus=200;
        			a[i][j]=2;
        			if(isWinornot()){
        				if(result==2){
        					a[i][j]=0;
        					return 10*i+j;
        				}
        			}
        			for(p=0;p<c_size;p++){
        				for(q=0;q<c_size;q++){
        					if(a[p][q]==0){
        						a[p][q]=1;
        						h=evaluate();
        						a[p][q]=0;
        						if(h<=plus){        //剪枝
        							minus=h;
        							q=c_size;
        							p=c_size;   //跳出两重循环
        						}
        						else{
        							if(h<minus)  minus=h;
        							
        						}
        						
        					}
        				}
        			}//二层节点
        			if(minus>plus&&minus!=200){
        				plus=minus;
        				max_i=i;
        				max_j=j;
        			}
        			a[i][j]=0;
        		}//endi
        	}//endfor
        }//endfor
        return 10*max_i+max_j;
         
    }
    private int evaluate(){
    	int i,j;
    	int h2,h1;
    	int h;
    	int a_temp[][]=new int[c_size][c_size];   //临时数组存储棋局信息
    	boolean tp;
    	h2=0;    //所有的空格放上电脑的棋子之后三子成一线的总数
    	h1=0;    //所有空格放上玩家的棋子之后三子成一线的总数
    	
    	if(isWinornot()){
    		if(result==2)  return 100;
    		else if(result==1)  return -100;
    		else{
    		}
    	}
    	//首先计算所有空格放上电脑的棋子之后三子三子成一线的个数
    	//将剩余的棋格都摆上电脑的棋子，即将a数组中值为0的数改为2
    	
    	for(i=0;i<c_size;i++){
    		for(j=0;j<c_size;j++){
    			if(a[i][j]==0)
    				a_temp[i][j]=2;
    			else
    			    a_temp[i][j]=a[i][j];
    		}
    	}
    	
    	
    	//计算横向上三子成一线的个数
    	for(i=0;i<c_size;i++){
    		
    		if(a_temp[i][0]==2){
    			tp=true;
    			for(j=1;j<c_size;j++){
    				if(a_temp[i][j]!=2)
    				{  
    					tp=false;
    					break;
    				}    
    			}
    			if(tp){
    				h2++;
    			}
    		}
    	}
    	
    	///计算竖向上三子成一线的个数
    	for(j=0;j<c_size;j++){
    		if(a_temp[0][j]==2){
    			tp=true;
    			for(i=1;i<c_size;i++){
    				if(a_temp[i][j]!=2){
    					tp=false;
    					break;
    				}
    			}
    			if(tp){
    				h2++;
    			}
    		}
    	}
    	
    	//计算两条斜对角线是否成一条线
    	if(a_temp[0][0]==2){
    		tp=true;
    		for(i=1;i<c_size;i++){
    			if(a_temp[i][i]!=2){
    				tp=false;
    				break;
    			}
    		}
    		if(tp)
    			h2++;
    	}
    	
    	if(a_temp[0][c_size-1]==2){
    		tp=true;
    		for(i=1,j=c_size-2;i<c_size;i++,j--){
    			if(a_temp[i][j]!=2){
    				tp=false;
    				break;
    			}
    		}
    		if(tp)
    			h2++;
    	}
 		
 		
 		//////计算所有空格都摆上玩家棋子是三子成一线的个数
 		//将剩余棋格改为玩家的棋子，即将a数组中值为0的数改为1
 		
 		for(i=0;i<c_size;i++){
    		for(j=0;j<c_size;j++){
    			if(a[i][j]==0)
    				a_temp[i][j]=1;
    			else
    			    a_temp[i][j]=a[i][j];
    		}
    	}
    	
    	
    	//计算横向上三子成一线的个数
    	for(i=0;i<c_size;i++){
    		
    		if(a_temp[i][0]==1){
    			tp=true;
    			for(j=1;j<c_size;j++){
    				if(a_temp[i][j]!=1)
    				{  
    					tp=false;
    					break;
    				}    
    			}
    			if(tp){
    				h1++;
    			}
    		}
    	}
    	
    	///计算竖向上三子成一线的个数
    	for(j=0;j<c_size;j++){
    		if(a_temp[0][j]==1){
    			tp=true;
    			for(i=1;i<c_size;i++){
    				if(a_temp[i][j]!=1){
    					tp=false;
    					break;
    				}
    			}
    			if(tp){
    				h1++;
    			}
    		}
    	}
    	
    	
    	//判断斜对角线是否是三子成一线
    	if(a_temp[0][0]==1){
    		tp=true;
    		for(i=1;i<c_size;i++){
    			if(a_temp[i][i]!=1){
    				tp=false;
    				break;
    			}
    		}
    		if(tp)
    			h1++;
    	}
    	
    	if(a_temp[0][c_size-1]==1){
    		tp=true;
    		for(i=1,j=c_size-2;i<c_size;i++,j--){
    			if(a_temp[i][j]!=1){
    				tp=false;
    				break;
    			}
    		}
    		if(tp)
    			h1++;
    	}
 		
 		
 		
 		h=h2-h1;
 		return h;   	
    	
    }
}