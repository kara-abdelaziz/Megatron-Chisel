# Megatron-Chisel Project

The project Megatron-chisel is an implementation of [Megatron project](https://www.el-kalam.com/projets/projet-megatron/) 
 of the hardware description language [Chisel](https://www.chisel-lang.org/). 
Chisel generate at the end System Verilog files (possible to generate Verilog also), 
that can be used in hardware simulation or a bitstream loaded to an FPGA with some additional specific configurations.

## How to test Megatron-Chisel

The first way to test Megatron-Chisel is to use the official Chisel tester tool called [Chiseltest](https://github.com/ucb-bar/chiseltest), 
it is a library used to perform formal verification for hardware circuits.

The second way, it is also a very effectif way, by inspecting the simulation signal waveformes using [GTKWave](https://gtkwave.sourceforge.net/).

But the ultimate way to test Megatron-Chisel is evidently by writing some programs or pieces of code and running the clock to inspect the results after execution. 
You have already two preprogrammed algorithms, fibonacci and factorial. Written in binary or machine language, 
after translation from assembly language (done previously on [Megatron project](https://www.el-kalam.com/projets/projet-megatron/)). 
The programs are saved in hexadecimal format in .ROM text files, and loaded in the ROM while starting the machine.

## Installing Chisel laguage on windows and using VS Code

In my personal experience, chisel wasn't fully compatible with windows. In my opinion, 
it is better to make it work on Linux emulated under Windows, and this is what we are going to explain in this section.

There are many ways to emulate Linux under Windows, like using [virtual machines](https://en.wikipedia.org/wiki/Virtual_machine), 
using libraries and toolboxes that provide Unix-like environement, like [MSYS](https://www.msys2.org/) and [MinGW](https://www.mingw-w64.org/). 
Or in our case using [WSL](https://en.wikipedia.org/wiki/Windows_Subsystem_for_Linux), Windows Subsystem for Linux, 
provided by Windows to run a Linux environement on Windows.

### 1. Installing WSL

This step is about installing Linux environment while will host Chisel language. It could be installed as follows :
- Launch the Windows command-line by typing CMD in the research textbox of the windows taskbar (it is also possible to use PowerShell instead if it is installed). Then type the command :
```sh
wsl --install
```
- Proceed the following steps of the installation.
- At a certain point it is required to create a Linux user account and its corresponding password. Mandatory later to use Linux commands.
- It is also possible to get help following this [WSL installation tutorial](https://learn.microsoft.com/en-us/windows/wsl/install).

### 2. Installing Scala/Chisel tools and dependencies

Chisel is based on [Scala](https://www.scala-lang.org/) as a [domain-specific language](https://en.wikipedia.org/wiki/Domain-specific_language) (DSL). 
It means that Chisel is a language created upon Scala language and inherit its properties and principles. 
The following steps allow the installation of the tools and dependencies needed for Scala/Chisel to compile under Linux.

1. Launch of WSL from Windows programs, and preparing the linux installation tool using the command (password required) :
```sh
sudo apt-get update
```

2. installing Java Runtime Environment :
```sh
sudo apt-get install default-jre
```

3. installing Scala Building Tool SBT (it is possible to look for a newer versions):
```sh
curl -s -L https://github.com/sbt/sbt/releases/download/v1.9.7/sbt-1.9.7.tgz | tar xvz
sudo mv sbt/bin/sbt /usr/local/bin/
```

4. installing the signal wave viewer GTKwave :
```sh
sudo apt-get install gtkwave
```

5. installing Verilator, the Chisel hardware simulator. But first we need the necessary tools to build it :
```sh
sudo apt-get install git help2man perl python3 make autoconf g++ flex bison ccache
sudo apt-get install libgoogle-perftools-dev numactl perl-doc
sudo apt-get install libfl2
sudo apt-get install libfl-dev
sudo apt-get install zlib1g zlib1g-dev
```
After installing the building tools, the next sequence of commands is to download its source code and build it
```sh
git clone https://github.com/verilator/verilator 

unset VERILATOR_ROOT
cd verilator

git pull
git checkout v5.024

autoconf
./configure
make -j `nproc`
sudo make install
```
It is possible to look for more recent version that v5.024. 
It is also possible to check the following [guide](https://verilator.org/guide/latest/install.html#installation) for additional information.

6. From now on, it is possible to compile, run, and simulate digital circuit designed by Chisel using only command-line.

For instance, you downloaded the [Megatron-Chisel](https://github.com/kara-abdelaziz/Megatron-Chisel/archive/refs/heads/main.zip)
directory and you acceded to the directory containing the file **build.sbt**. It is now possible to launch scala builder SBT like this :
```sh
sbt
```
It is possible to compile all the project files with the command :
```sh
compile
```
Run, for example the source file **Megatron.scala** by the command :
```sh
runMain megatron.mainMegatron
```
Or perform a test written in the file **test_megatron.scala** by the command (**DUT_megatron** is a class defined in **test_megatron.scala** file) :
```sh
testOnly DUT_megatron
```
When finishing with SBT, it is possible to quit by :
```sh
exit
```
It is also possible to do a globe compile, run, and test of the entire project without entering SBT and do it file by file, with those 3 commands :
```sh
sbt compile
sbt run
sbt test
```
6. After a correct compile, run, and test you get a corresponding generated System Verilog (.sv) file inside the directory **generated**, that can be used in a simulator or an FPGA.
 
7. It is also possible to view a waveform model using GKTwave, created alongside the test process. For example, it is possible to view the waveform of the entire Megatron circuit using this command :
```sh
gtkwave -f test_run_dir/DUT_megatron_should_be_able_to_perform_a_Fibonacci_sequence/Megatron.vcd
```
The .vcd file containing the waveform was generated in the test process phase.

### 3. Installing VS Code

[VS Code](https://code.visualstudio.com/) was the IDE used to develop Megatron-Chisel in Chisel. Two additional extensions to VS Code were installed, **Chisel syntax** and **WSL**. 
The first one used for the syntax highlighting, and the second to integrate the installed WSL command-line within VS Code. The sceenshot below gives an overview of the IDE configuration.

![vscode screenshot](screen-shot.jpg)

A third popular extension called **Metal** exist, used the most often with Scala. But, concidring my personal experience, it is not fully compatible with Chisel. I prefer waiting for its future updates.

## Project file structure

Scala programs have a particular files/directory structure. An emphasis is made by Scala language to separate the code from the test. Then in any Scala program, the source code should be located in the directory **/src/main/scala**, and the test code in the directory **/src/test/scala**.

The Megatron-Chisel project is composed of the following files :

### Build.sbt
This file is the project configuration file, and should always been located in the project directory root. The root is also the location where **sbt** should be executed.

### Source code

Megatron-Chisel source code files are as follows (located in **/src/main/scala**) :

1. Megatron.scala : The main file that contains the whole project.
2. components.scala : This file contains the hardware definition of the basic components of Megatron, like the registers, counters, and shifters.
3. ALU.scala : The file that contains the hardware definition of the Arithmetic and Logic Unit.
4. DataPath.scala : The file that contains the definition of the datapath.
5. CU.scala : The file that contains the definition of the Control Unit.
6. MAU.scala : The file that contains the definition of the Memory Addressing Unit.
7. IOU.scala : The file that contains the definition of the Input/Output Unit.
8. RAM.scala : The file that contains the definition of Megatron RAM.
9. ROM.scala : The file that contains the definition of Megatron ROM.

### Test code

Megatron-Chisel test code files are as follows (located in **/src/test/scala**) :

1. test_megatron.scala : This file tests the Fibonacci and Factorial programs on Megatron.
2. test_component.scala : This file contains a set of test units for the different components of Megatron.
3. test_CU.scala : The file contains an exhaustive set of test units for the different instruction opcodes of Megatron.
4. test_CU.scala : many test units are executed to check the datapath.

Even though Megatron passed an important number of tests, but in my opinion, a more sophisticated test process needs to be implemented to go through all test possibilities.

### Resources

The resources of a Scala program are most often stored in the directory **/src/main/ressource**. 
Megatron-Chisel resources files are as follows :

1. Factorial.hex : The ROM memory image of the factorial test program. The program is written in hexadecimal, and loaded in ROM at start-up of the machine.
2. Fibonacci.hex : The ROM memory image of the fibonacci test program. The program is written in hexadecimal, and loaded in ROM at start-up of the machine.
3. ROM.hex :  A ROM memory image with some values used by test code. The image is loaded in ROM at start-up of the machine.
4. RAM.hex :  A RAM memory image with some values used by test code. The image is loaded in RAM at start-up of the machine.

### Waveforms

Waveforms are stored in files with the extension **.vcd**. They are stored in the directory **/test_run_dir** which is ignored by git. Then, they are only generated in local-host.

Waveform file (vcd) needs the annotation **.withAnnotations(Seq(WriteVcdAnnotation))** added to the test object inside the test file, to be generated.

## Website

The project website for more information : [el-kalam.com](https://www.el-kalam.com/)
