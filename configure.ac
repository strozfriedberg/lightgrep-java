# Process this file with autoconf to produce a configure script.
# TODO: Unknown whether 2.64 works; was a bit of a milestone release, though
AC_PREREQ([2.64])

AC_INIT([jlightgrep], [0.0], [BUG-REPORT-ADDRESS])

AC_CONFIG_AUX_DIR([config])
AC_CONFIG_MACRO_DIR([m4])
AC_CONFIG_SRCDIR([src/jni/jlightgrep.cpp])
AC_CONFIG_HEADERS([config.h])

AM_INIT_AUTOMAKE([foreign subdir-objects])
LT_INIT

AC_PROG_CXX
AM_PROG_CC_C_O

AC_LANG([C++])

# Get the C++11 flag, but don't append it to CPPFLAGS
CXXFLAGS_saved="$CXXFLAGS"
AX_CXX_COMPILE_STDCXX_11([noext], [mandatory])
CXX11_FLAG=`echo $CXXFLAGS | sed -e 's/.* //'`
CXXFLAGS="$CXXFLAGS_saved"

AX_APPEND_COMPILE_FLAGS([-W -Wall -Wextra -Wnon-virtual-dtor -pedantic -pipe -O3 $CXX11_FLAG], [JNI_CXXFLAGS])

#
# liblightgrep
#

PKG_CHECK_MODULES([LG], [lightgrep])

LG_CPPFLAGS="$LG_CFLAGS"
LG_CFLAGS=""

AC_SUBST([LG_CPPFLAGS])
AC_SUBST([LG_CXXFLAGS])
AC_SUBST([LG_LDFLAGS])

#
# Java
#

AC_PATH_PROG([JAVA], [java])
AC_PATH_PROG([JAR], [jar],)
AC_PATH_PROG([JAVAH], [javah])

# NB: AX_JNI_INCLUDE_DIR checks javac for us, but doesn't set it. Doh.
AX_JNI_INCLUDE_DIR
for JNI_INCLUDE_DIR in $JNI_INCLUDE_DIRS; do
  JNI_CPPFLAGS="$JNI_CPPFLAGS -I$JNI_INCLUDE_DIR"
done

JAVAC=$(which javac)
AC_SUBST([JAVAC])

AC_SUBST([JNI_CPPFLAGS])
AC_SUBST([JNI_CXXFLAGS])
AC_SUBST([JNI_LDFLAGS])

JUNIT_JARS=/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar
AC_SUBST([JUNIT_JARS])

AC_CONFIG_FILES([Makefile])
AC_OUTPUT
