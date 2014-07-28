// #include <stdio.h>
// #include <string.h>
// #include <errno.h>
// #include <stdlib.h>
// #include <pcre.h>
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

// #define M 1<<7
// #define OVECCOUNT 30
// bool isxdigit(char s)
// {
// 	if((s>=48&&s<=57)||(s>='A'&&s<='F')||(s>='a'&&s<='f'))
// 		return true;
// 	return false;
// }

// int hex2dec(char s)
// {
// 	if(s>=48&&s<=57)
// 		return s-48;
// 	if(s>='A'&&s<='F')
// 		return s-'A'+10;
// 	if(s>='a'&&s<='f')
// 		return s-'a'+10;
// }


// char* pre_process(const char * s) 
// {
//   char * result = (char *)malloc(strlen(s) + 1);
//   unsigned int sindex = 0, rindex = 0;
//   while( sindex < strlen(s) ) {
//     if( sindex + 3 < strlen(s) && s[sindex] == '\\' && s[sindex+1] == 'x' && 
// 	isxdigit(s[sindex + 2]) && isxdigit(s[sindex + 3]) ){

//       result[rindex] = hex2dec(s[sindex + 2])*16 + hex2dec(s[sindex + 3]);

//       switch ( result[rindex] ) {
//         case '$':
//         case '(':
//         case ')':
//         case '*':
//         case '+':
//         case '.':
//         case '?':
//         case '[':
//         case ']':
//         case '^':
//         case '|':
//         case '{':
//         case '}':
//         case '\\':
//           printf("%c%c%c%c   %cWrong HexString!\n",s[sindex],s[sindex+1],s[sindex+2],s[sindex+3],result[rindex]);
//           break;
//         case '\0':
//           printf("Warning: null (\\x00) in layer7 regexp.A null terminates the regexp string!\n");
// 	  	  break;
//         default:
//           break;
//       }
//       sindex += 3;
//     }
//     else
//       result[rindex] = s[sindex];

//     sindex++; 
//     rindex++;
//   }
//   result[rindex] = '\0';

//   return result;
// }
  

int main(int argc,char **argv)
{

    cout << "fdsf" << endl;
  stringstream sline;
  string line, proto;
  int mk;

  unsigned int markmask = 0xffffffff;
  int masknbits = 32;
  int discussedbitusage = 0;
  int maskfirstbit = 0;

  line = "http 18";
  sline << line;
  if(!(sline >> proto) || !(sline >> mk))
  {
      cerr << "Ignoring line because it isn't in the format 'protocol mark':"
           << line << endl;
  }

    if(mk <= 1 || mk >= (markmask >> maskfirstbit) ){
      cerr << "Ignoring line because the mark is not in the range 3-"
           << (markmask >> maskfirstbit) << ":\n" << line << endl;
      if(!discussedbitusage){
        cerr << "Your mask allows me to use " << masknbits 
             << " bits, and the values 0, 1 and 2 have special meanings.\n";
        discussedbitusage = 1;
      }
      // continue;
    }


  cout << "result" << endl;
  cout << "proto: " << proto << endl << "mk: " << mk << endl;

  cout << ">>" << (markmask >> maskfirstbit) << endl;

    // const char * matcher = "thisisastring";
    // pcre *repattern;
    // const char *pattern = "thisisastringfsdfsdf";
    // int len = strlen(matcher);
    // int resu = 0;
    // const char *error;
    // int erroffset;
    // //printf("%d\n",length);
    // //printf("This is true!\n");
    // int ovector[OVECCOUNT];
    // // for(i=0;i<100000;i++)
    // // {
    // // for(j=0;j<100;j++)
    // // {
    // repattern=pcre_compile(pattern, PCRE_CASELESS, &error, &erroffset, NULL);
    // resu=pcre_exec(repattern, NULL, matcher, len, 0, 0, ovector, OVECCOUNT);
    // // }
    // // }
    // if(resu<0) 
    //   return false;
    // else 
    //   return true;
    // free(repattern);
 }