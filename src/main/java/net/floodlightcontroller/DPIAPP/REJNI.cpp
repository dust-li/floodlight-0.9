#include "net_floodlightcontroller_DPIAPP_REJNI.h"

#include <string.h>
#include <errno.h>
#include <stdlib.h>
#include <pcre.h>

#define M 1<<7
#define OVECCOUNT 30
//bool isxdigit(char s)
//{
//	if((s>=48&&s<=57)||(s>='A'&&s<='F')||(s>='a'&&s<='f'))
//		return true;
//	return false;
//}
//
//int hex2dec(char s)
//{
//	if(s>=48&&s<=57)
//		return s-48;
//	if(s>='A'&&s<='F')
//		return s-'A'+10;
//	if(s>='a'&&s<='f')
//		return s-'a'+10;
//}
//
//
//char* pre_process(const char * s)
//{
//  char * result = (char *)malloc(strlen(s) + 1);
//  unsigned int sindex = 0, rindex = 0;
//  while( sindex < strlen(s) ) {
//    if( sindex + 3 < strlen(s) && s[sindex] == '\\' && s[sindex+1] == 'x' &&
//	isxdigit(s[sindex + 2]) && isxdigit(s[sindex + 3]) ){
//
//      result[rindex] = hex2dec(s[sindex + 2])*16 + hex2dec(s[sindex + 3]);
//
//      switch ( result[rindex] ) {
//        case '$':
//        case '(':
//        case ')':
//        case '*':
//        case '+':
//        case '.':
//        case '?':
//        case '[':
//        case ']':
//        case '^':
//        case '|':
//        case '{':
//        case '}':
//        case '\\':
//          printf("%c%c%c%c   %cWrong HexString!\n",s[sindex],s[sindex+1],s[sindex+2],s[sindex+3],result[rindex]);
//          break;
//        case '\0':
//          printf("Warning: null (\\x00) in layer7 regexp.A null terminates the regexp string!\n");
//	  	  break;
//        default:
//          break;
//      }
//      sindex += 3;
//    }
//    else
//      result[rindex] = s[sindex];
//
//    sindex++;
//    rindex++;
//  }
//  result[rindex] = '\0';
//
//  return result;
//}


JNIEXPORT jboolean JNICALL Java_net_floodlightcontroller_DPIAPP_REJNI_Match
  (JNIEnv *env, jclass jcl, jstring patt, jbyteArray mat, jint len)
  {
  	 const char * pattern = env->GetStringUTFChars(patt, 0);

  	 jbyte * match  = env->GetByteArrayElements(mat,0);

  	 char *	matcher = (char *)match;
  	 pcre *repattern;
  	 int resu = 0;
  	 const char *error;
     int erroffset;
     int ovector[OVECCOUNT];
     repattern=pcre_compile(pattern, PCRE_CASELESS, &error, &erroffset, NULL);
     resu=pcre_exec(repattern, NULL, matcher, len, 0, 0, ovector, OVECCOUNT);


  	 env->ReleaseStringUTFChars(patt, pattern);
  	 env->ReleaseByteArrayElements(mat, match, 0);

  	 free(repattern);

  	 if(resu<0) return false;
  	 else
  		 return true;
  }
  
