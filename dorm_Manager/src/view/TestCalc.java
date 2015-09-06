package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class TestCalc extends JFrame implements ActionListener, KeyListener {
 // 运算结果的导出	
 public String result;
 public JLabel othersLabel;
 private static final long serialVersionUID = 1L;
 // 面板容器
 private Container container;
 // 计算器结果栏
 private JLabel resultLabel;
 // 数字键0-9
 private JButton[] numbers;
 // 退格键、清除键
 private JButton backspace, clear;
 // +,-,*,/ 四个操作符
 private JButton add, sub, mul, div, sqrt;
 // 特殊功能正负号，小数点按钮
 private JButton fushu, dot;
 // 结果按钮
 private JButton equal;
 // 导出按钮
 private JButton export;
 // 保存数据链
 private LinkedList<String> datalist;
 // 判断是否按下等号键,初始设置为false
 boolean isPressEqualButton = false;
 // 构造函数
 public TestCalc() {
  // 1、绘制计算器的窗体大小
  this.setTitle("个人版的计算器—Calculator");
  this.setSize(400, 400);
  this.setLocation(200, 200);
  this.setResizable(false);//用户不能调整窗口大小
  // 生成LinkedList的一个实例
  datalist = new LinkedList<String>();
  // 2、绘制panel容器的初始值
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  // 网格形式对容器的组件进行布置,创建具有指定行数和列数的网格布局。
  panel1.setLayout(new GridLayout(1, 5));
  panel2.setLayout(new GridLayout(4, 5));
  // 3、第一个容器用来加载结果显示栏内容
  resultLabel = new JLabel("0");
  resultLabel.setFont(new Font("楷体",0,50));
  // resultLabel.setText("0");
  // 设置水平居右对其
  resultLabel.setHorizontalAlignment(JLabel.RIGHT);
  resultLabel.setEnabled(false);// 不可编辑
  
 
  panel1.setBackground(Color.WHITE);
  panel1.add(resultLabel);

  // 4、设置第二个容器用来加载数字键和其他按钮
  clear = new JButton("C");
  clear.setFont(new Font("楷体",0,20));
  clear.addActionListener(this);// 增加事件监听
  sqrt = new JButton("sqrt");
  sqrt.setFont(new Font("楷体",0,20));
  backspace = new JButton("退格");
  backspace.setFont(new Font("楷体",0,20));
  backspace.addActionListener(this);
  // 初始化设置数字按钮
  numbers = new JButton[10];
  for (int i = 0; i < 10; i++){
   numbers[i] = new JButton("" + i);// 类型自动转换
   numbers[i].setFont(new Font("楷体",0,20));
  }
  add = new JButton("+");
  add.setFont(new Font("楷体",0,20));
  sub = new JButton("-");
  sub.setFont(new Font("楷体",0,20));
  mul = new JButton("×");
  mul.setFont(new Font("楷体",0,20));
  div = new JButton("÷");
  div.setFont(new Font("楷体",0,20));
  equal = new JButton("＝");
  equal.setFont(new Font("楷体",0,20));
  fushu = new JButton("+/-");
  fushu.setFont(new Font("楷体",0,20));
  dot = new JButton(".");
  dot.setFont(new Font("楷体",0,20));
  
  export=new JButton("导出");
 
  export.setFont(new Font("黑体",0,20));
  // 给数字键增加事件监听
  for (int i = 0; i < 10; i++)
   numbers[i].addActionListener(this);
  add.addActionListener(this);
  sub.addActionListener(this);
  mul.addActionListener(this);
  div.addActionListener(this);
  equal.addActionListener(this);
  fushu.addActionListener(this);
  dot.addActionListener(this);
  sqrt.addActionListener(this);
  export.addActionListener(this);
  // 增加键盘监听事件
  for (int i = 0; i < 10; i++)
   numbers[i].addKeyListener(this);
  add.addKeyListener(this);
  sub.addKeyListener(this);
  mul.addKeyListener(this);
  div.addKeyListener(this);
  equal.addKeyListener(this);
  fushu.addKeyListener(this);
  dot.addKeyListener(this);
  sqrt.addKeyListener(this);
  clear.addKeyListener(this);
  backspace.addKeyListener(this);

  // 加入面板2
  panel2.add(numbers[7]);// 增加第一行
  panel2.add(numbers[8]);
  panel2.add(numbers[9]);
  panel2.add(add);
  panel2.add(clear);
  // 增加第二行
  panel2.add(numbers[4]);
  panel2.add(numbers[5]);
  panel2.add(numbers[6]);
  panel2.add(sub);
  panel2.add(backspace);
  // 增加第三行
  panel2.add(numbers[1]);
  panel2.add(numbers[2]);
  panel2.add(numbers[3]);
  panel2.add(mul);
  panel2.add(sqrt);
  // 增加地四行
  panel2.add(numbers[0]);
  panel2.add(fushu);
  panel2.add(dot);
  panel2.add(div);
  panel2.add(equal);
  

  
  // 加入所有面板到容器中
  container = this.getContentPane();
  container.add(panel1, "North");
  container.add(panel2, "Center");
  container.add(export,"South");
  this.addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent e) {
    System.exit(0);
   }
  });// 添加指定的窗口侦听器，以从此窗口接收窗口事件
 }

 public void actionPerformed(ActionEvent ae) {
  JButton btn = (JButton) ae.getSource();
  // 鼠标按下数字键
  if (btn == numbers[0] || btn == numbers[1] || btn == numbers[2]
    || btn == numbers[3] || btn == numbers[4] || btn == numbers[5]
    || btn == numbers[6] || btn == numbers[7] || btn == numbers[8]
    || btn == numbers[9]) {
   execNumber(btn);
  } else if (btn == add || btn == sub || btn == mul || btn == div) {// 鼠标按下操作符键
   execOperation(btn);
  } else if (btn == equal) {// 鼠标按下等于号
   execResult();
  } else if (btn == dot) {// 鼠标按下点号
   execDot();
  } else if (btn == fushu) {// 鼠标按下正负号
   execFushu();
  } else if (btn == clear) {// 鼠标按下清除键
   execClear();
  } else if (btn == backspace) {// 鼠标按下退格键
   execBackSpace();
  } else if (btn == sqrt) {
   exeSqrt();
  }
  else if (btn == export) {//鼠标按下导出键
	
	  export();
	  
	  this.setVisible(false);
	  System.out.println("export:"+result);
 }
 }

 // 计算开根号
 private void export(){
	 result=resultLabel.getText();
	 othersLabel.setText(resultLabel.getText());
 }
 private void exeSqrt() {
  double sqrtResult = 0.0d;
  if (datalist.size() >= 1 && datalist.size() <= 3)// 列表为1的情况，直接开根号。为0时不处理（无意义）
  {// 为2时直接把第一个元素的开方存入第三个元素上，参照windows计算器
   // 列表为3时，开方存入第三个元素中
   String str = datalist.getFirst();// 满足1，2情况
   if (Double.parseDouble(str) < 0)//判断无效的参数
   {
    datalist.clear();
    resultLabel.setText("输入函数无效。");
   }
   else {
    sqrtResult = Math.sqrt(Double.parseDouble(str));
    if (datalist.size() == 1)
     datalist.set(0, "" + sqrtResult);// 写入
    else if (datalist.size() == 2)
     datalist.add("" + sqrtResult);// 因为没有第三个元素，所以加入
    else if (datalist.size() == 3) {
     str = datalist.getLast();// 满足3的情况
     sqrtResult = Math.sqrt(Double.parseDouble(str));
     datalist.set(2, "" + sqrtResult);// 写入
    }
    resultLabel.setText("" + sqrtResult);// 回显
   }
  }
 }

 // 当按键为退格符时：长度为1，3时分别对一、三操作数进行操作：若该节点的长度大于或等于1，
 // 则设置其值为原内容字符串的字串substring(0,num.length()-1)；
 // 否则移除列表中的数据
 private void execBackSpace() {
  if(isPressEqualButton==true)
  {
//   表明是按下等号键后的结果值，此时不允许有数据退格的出现。
  }else if (datalist.size() == 1 || datalist.size() == 3) {
   String Str = datalist.getLast();
   if (Str.length() == 1) {
    if (datalist.size() == 1)// 判断是那个位置上的退格
     datalist.set(0, "0");
    else if (datalist.size() == 3)
     datalist.set(2, "0");
    resultLabel.setText("" + 0);
   } else if (Str.length() > 1) {
    Str = Str.substring(0, Str.length() - 1);
    if (datalist.size() == 1)
     datalist.set(0, Str);// 保存数据
    else if (datalist.size() == 3)
     datalist.set(2, Str);// 保存数据

    resultLabel.setText(Str);// 显示退格后的数据
   } else {
    datalist.removeLast();
    resultLabel.setText("0");
   }
  }
 }

 // 执行清除功能
 private void execClear() {
  // 相当回复所有数据为默认值
  datalist.clear();
  isPressEqualButton = false;
  resultLabel.setText("0");
 }

 // 执行正负号功能
 private void execFushu() {
  if (datalist.size() == 1 || datalist.size() == 3) {
   String str = datalist.getLast();
   double dou = Double.parseDouble(str);
   dou = -1 * dou;
   if (datalist.size() == 1)
    datalist.set(0, String.valueOf(dou));
   else if (datalist.size() == 3)
    datalist.set(2, String.valueOf(dou));
   resultLabel.setText("" + dou);
  }
 }

 // 执行小数点功能
 private void execDot() {
  // 当第一次直接点击小数点的时候自动变为0.*的模式
  if (datalist.size() == 0) {
   datalist.add("0.");// 首次存入数据
   resultLabel.setText("0.");
  } else if (datalist.size() == 1 || datalist.size() == 3) {
   String str = datalist.getLast();
   if (str.indexOf('.') == -1)// 说明未找到小数点
   {
    str = str + ".";// 运算符算法操作效率高
    if (datalist.size() == 1) {
     datalist.set(0, str);
    } else {
     datalist.set(2, str);
    }
    resultLabel.setText(str);// 回显结果
   }
  }
  // 判断列表中已有2个，再直接点击点号时说明第三个数又是以0.*开头
  else if (datalist.size() == 2) {
   datalist.add("0.");
   resultLabel.setText("0.");
  }
 }

 // 执行运行结果功能
 private void execResult() {
  isPressEqualButton = true;// 设置此键为true
  // 当列表长度为1或者2时，说明不能计算，保持数据不变
  if (datalist.size() > 0 && datalist.size() < 3) {//0~2的情况集合
   String str = datalist.getFirst();
   resultLabel.setText("" + Double.parseDouble(str));//去除小数点后面多余追加的零
   datalist.clear();// 清空列表数据
   datalist.add(str);// 加入这个结果
  } else if (datalist.size() == 3) {
   double result = 0.0d;
   int temp=0;//去掉结果为整数时所带的小数点
   String num1 = datalist.getFirst();
   String op = datalist.get(1);
   String num2 = datalist.getLast();
   if (op.equals("+")) {
    result = Double.parseDouble(num1) + Double.parseDouble(num2);
   } else if (op.equals("-")) {
    result = Double.parseDouble(num1) - Double.parseDouble(num2);
   } else if (op.equals("*")) {
    result = Double.parseDouble(num1) * Double.parseDouble(num2);
   } else if (op.equals("/")) {
    result = Double.parseDouble(num1) / Double.parseDouble(num2);
   }
   datalist.clear();// 清空
   datalist.add("" + result);// 使得结果加入列表中
   temp=(int)result;
   if(Double.isInfinite(result)==true)//判断结果是否为Infinity，即除数为零
   {
    datalist.clear();//再次清空列表数据
    resultLabel.setText("除数不能为零。");// 回显错误结果
   }else if(Double.isNaN(result)==true)//判断结果是否为NaN，即0/0类型
   {
    datalist.clear();//再次清空列表数据
    resultLabel.setText("函数结果未定义。");// 回显错误结果
   }else if((result-temp)==0)
    resultLabel.setText("" + temp);// 回显结果
   else
    resultLabel.setText("" + result);// 回显结果
  }
 }

 // 执行操作功能
 private void execOperation(JButton btn) {
  String sign = null;
  if (btn == add) {
   sign = "+";
  } else if (btn == sub) {
   sign = "-";
  } else if (btn == mul) {
   sign = "*";
  } else if (btn == div) {
   sign = "/";
  }
  // 根据列表中元素的个数判断增加,列表元素为1时
  if (datalist.size() == 1) {
   datalist.add(sign);// 追加操作符
  }
  // 列表中元素为2，说明要替换现有的操作符
  else if (datalist.size() == 2) {
   datalist.set(1, sign);
  }
  // 列表中元素为3时，则取出前两个进行计算
  else if (datalist.size() == 3) {
   execResult();// 调用此方法执行，然后恢复等号键为false
   isPressEqualButton = false;
   datalist.add(sign);
  }
 }

 // 执行数字解析功能
 private void execNumber(JButton btn) {
  int num = 0;
  if (btn == numbers[0])
   num = 0;
  else if (btn == numbers[1])
   num = 1;
  else if (btn == numbers[2])
   num = 2;
  else if (btn == numbers[3])
   num = 3;
  else if (btn == numbers[4])
   num = 4;
  else if (btn == numbers[5])
   num = 5;
  else if (btn == numbers[6])
   num = 6;
  else if (btn == numbers[7])
   num = 7;
  else if (btn == numbers[8])
   num = 8;
  else if (btn == numbers[9])
   num = 9;
  // 列表为空的情况,加数到链表中，并设置是否按下等号键为false
  if (datalist.size() == 0) {
   datalist.add("" + num);
   resultLabel.setText("" + num);
   isPressEqualButton = false;
  }
  // 如果列表长度为1,取数合并结果
  else if ((datalist.size() == 1) && (isPressEqualButton == false))// 说明还是第一个数据中的
  {
   String str = datalist.getFirst();
   if (str.equals("0"))// 清除以0开头的连续数字
    str = "";
   String str0 = str.concat(String.valueOf(num));
   // 覆盖列表中第一元素的内容
   datalist.set(0, str0);
   resultLabel.setText(str0);
  } else if ((datalist.size() == 1) && (isPressEqualButton == true))// 表示已经是另外一个数据，上一个作废
  {
   datalist.set(0, String.valueOf(num));// 覆盖
   resultLabel.setText("" + num);// 显示
   isPressEqualButton = false;// 还原
  }
  // 列表为2，则加入第二个操作数
  else if (datalist.size() == 2) {
   datalist.add("" + num);
   resultLabel.setText("" + num);
  }
  // 列表为3，表示继续输入的数据为第二操作数
  else if (datalist.size() == 3) {
   String str = datalist.getLast();
   if (str.equals("0"))
    str = "";
   String strLast = str.concat("" + num);
   // 设置第二操作数的更新
   datalist.set(2, strLast);
   resultLabel.setText(strLast);
  }
 }

 // 键盘事件处理函数
 public void keyPressed(KeyEvent ke) {
  JButton btn = null;
  // 获取键盘值，分别从主键盘区和辅助键(数字键盘)区
  if (ke.getKeyCode() == KeyEvent.VK_0
    || ke.getKeyCode() == KeyEvent.VK_1
    || ke.getKeyCode() == KeyEvent.VK_2
    || ke.getKeyCode() == KeyEvent.VK_3
    || ke.getKeyCode() == KeyEvent.VK_4
    || ke.getKeyCode() == KeyEvent.VK_5
    || ke.getKeyCode() == KeyEvent.VK_6
    || ke.getKeyCode() == KeyEvent.VK_7
    || ke.getKeyCode() == KeyEvent.VK_8
    || ke.getKeyCode() == KeyEvent.VK_9
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD0
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD1
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD2
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD3
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD4
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD5
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD6
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD7
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD8
    || ke.getKeyCode() == KeyEvent.VK_NUMPAD9) {
   btn = numbers[ke.getKeyChar() - 48];//与相应按钮捆绑，仅是为了使用同一函数而已
   execNumber(btn);
  } else if (ke.getKeyCode() == KeyEvent.VK_ADD) {
   btn = add;
   execOperation(btn);
  } else if (ke.getKeyCode() == KeyEvent.VK_SUBTRACT) {
   btn = sub;
   execOperation(btn);
  } else if (ke.getKeyCode() == KeyEvent.VK_MULTIPLY) {
   btn = mul;
   execOperation(btn);
  } else if (ke.getKeyCode() == KeyEvent.VK_DIVIDE) {
   btn = div;
   execOperation(btn);
  } else if (ke.getKeyCode() == KeyEvent.VK_ENTER   //支持回车键
    || ke.getKeyCode() == KeyEvent.VK_EQUALS) {// 支持等号键
   execResult();
  } else if (ke.getKeyCode() == KeyEvent.VK_DECIMAL) {// 键盘按下点号
   execDot();
  } else if (ke.getKeyCode() == KeyEvent.VK_MINUS) {// 主键盘区的正负号
   execFushu();
  } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {// 主键盘区按下Esc键，清除显示内容
   execClear();
  } else if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {//主键盘区按下backSpace键,进行退格
   execBackSpace();
  } else if (ke.getKeyCode() == KeyEvent.VK_S) {//特定设置主键盘区按下字母S键，进行开方
   exeSqrt();
  }
 }

 public void keyReleased(KeyEvent ke) {
 }

 public void keyTyped(KeyEvent ke) {
 }
 
 // 函数入口main方法
 public static void main(String args[]) {
  TestCalc calc = new TestCalc();
  calc.setVisible(true);
 }

public JLabel getOthersLabel() {
	return othersLabel;
}

public void setOthersLabel(JLabel othersLabel) {
	this.othersLabel = othersLabel;
}
}

