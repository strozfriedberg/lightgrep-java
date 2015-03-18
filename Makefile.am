ACLOCAL_AMFLAGS = -I m4

AM_CPPFLAGS = -Iinclude $(JNI_CPPFLAGS) $(LG_CPPFLAGS)
AM_CXXFLAGS = $(STDCXX_CXXFLAGS) $(JNI_CXXFLAGS) $(LG_CXXFLAGS)
AM_LDFLAGS = $(JNI_LDFLAGS) $(LG_LDFLAGS) -module -avoid-version

noinst_jar_DATA = src/java/src/jlightgrep.jar
noinst_jardir = $(libdir)

lib_LTLIBRARIES = src/jni/libjlightgrep.la

src_jni_libjlightgrep_la_SOURCES = src/jni/jlightgrep.cpp

src_jni_libjlightgrep_la_LIBADD = $(LG_LIBS) $(STDCXX_LIB)

SRC_DIR:=src/java/src
TEST_DIR:=src/java/test
PKG_DIR:=com/lightboxtechnologies/lightgrep

path_to_class = $(subst /,.,$(subst $(1)/,,$(basename $(2))))

SRC_SOURCE_FILES:=\
	$(SRC_DIR)/$(PKG_DIR)/ContextHandle.java \
	$(SRC_DIR)/$(PKG_DIR)/ContextOptions.java \
	$(SRC_DIR)/$(PKG_DIR)/DecoderHandle.java \
	$(SRC_DIR)/$(PKG_DIR)/FSMHandle.java \
  $(SRC_DIR)/$(PKG_DIR)/Handle.java \
	$(SRC_DIR)/$(PKG_DIR)/HitCallback.java \
	$(SRC_DIR)/$(PKG_DIR)/HitContext.java \
	$(SRC_DIR)/$(PKG_DIR)/KeyOptions.java \
	$(SRC_DIR)/$(PKG_DIR)/KeywordException.java \
	$(SRC_DIR)/$(PKG_DIR)/LGUtil.java \
	$(SRC_DIR)/$(PKG_DIR)/LibraryLoader.java \
	$(SRC_DIR)/$(PKG_DIR)/PatternHandle.java \
	$(SRC_DIR)/$(PKG_DIR)/PatternInfo.java \
	$(SRC_DIR)/$(PKG_DIR)/PatternMapHandle.java \
	$(SRC_DIR)/$(PKG_DIR)/ProgramException.java \
	$(SRC_DIR)/$(PKG_DIR)/ProgramHandle.java \
	$(SRC_DIR)/$(PKG_DIR)/ProgramOptions.java \
	$(SRC_DIR)/$(PKG_DIR)/SearchHit.java \
	$(SRC_DIR)/$(PKG_DIR)/Throws.java

SRC_CLASS_NAMES:=$(call path_to_class,$(SRC_DIR),$(SRC_SOURCE_FILES))
SRC_CLASS_FILES:=$(SRC_SOURCE_FILES:.java=.class)

src/jni/jlightgrep.lo: src/jni/jlightgrep.h

src/jni/jlightgrep.h: src/jni/jlightgrep.cpp $(SRC_SOURCE_FILES)
	$(JAVAH) -o $@ -jni -cp $(SRC_DIR) $(SRC_CLASS_NAMES)

src/java/src/jlightgrep.jar: $(SRC_CLASS_FILES)
	$(JAR) cf $@ -C $(SRC_DIR) .

$(SRC_CLASS_FILES): compile_src

compile_src: $(SRC_SOURCE_FILES)
	$(JAVAC) -d $(SRC_DIR) -cp $(SRC_DIR) -Xlint $^

TEST_SOURCE_FILES:=\
	$(TEST_DIR)/$(PKG_DIR)/AbstractDataDrivenTest.java \
	$(TEST_DIR)/$(PKG_DIR)/AbstractSearchTest.java \
	$(TEST_DIR)/$(PKG_DIR)/BaseGetHitContextTest.java \
	$(TEST_DIR)/$(PKG_DIR)/BaseSearchTest.java \
	$(TEST_DIR)/$(PKG_DIR)/BaseStartsWithTest.java \
	$(TEST_DIR)/$(PKG_DIR)/GetHitContextArrayTest.java \
	$(TEST_DIR)/$(PKG_DIR)/GetHitContextDirectByteBufferTest.java \
	$(TEST_DIR)/$(PKG_DIR)/GetHitContextWrappedByteBufferTest.java \
	$(TEST_DIR)/$(PKG_DIR)/LightgrepTest.java \
	$(TEST_DIR)/$(PKG_DIR)/SearchArrayTest.java \
	$(TEST_DIR)/$(PKG_DIR)/SearchDirectByteBufferTest.java \
	$(TEST_DIR)/$(PKG_DIR)/SearchWrappedByteBufferTest.java \
	$(TEST_DIR)/$(PKG_DIR)/StartsWithArrayTest.java \
	$(TEST_DIR)/$(PKG_DIR)/StartsWithDirectByteBufferTest.java \
	$(TEST_DIR)/$(PKG_DIR)/StartsWithWrappedByteBufferTest.java

TEST_CLASS_NAMES:=$(call path_to_class,$(TEST_DIR),$(TEST_SOURCE_FILES))
TEST_CLASS_FILES:=$(TEST_SOURCE_FILES:.java=.class)

$(TEST_CLASS_FILES): compile_test

TEST_CLASSPATH:=$(SRC_DIR):$(TEST_DIR):$(JUNIT_JAR)

compile_test: $(TEST_SOURCE_FILES) $(SRC_CLASS_FILES)
	$(JAVAC) -encoding UTF-8 -d $(TEST_DIR) -cp $(TEST_CLASSPATH) -Xlint $(filter %.java,$^)

TESTS = $(TEST_DIR)/run_test.sh

$(TEST_DIR)/run_test.sh: $(TEST_CLASS_FILES)
	echo -e "#!/bin/bash -ex\n\n$(JAVA) -cp $(TEST_CLASSPATH) -Djava.library.path=src/jni/.libs org.junit.runner.JUnitCore $(filter-out com.lightboxtechnologies.lightgrep.Base%,$(filter-out com.lightboxtechnologies.lightgrep.Abstract%,$(TEST_CLASS_NAMES)))" >$@
	chmod a+x $@

.PHONY: compile_src compile_test

MOSTLYCLEANFILES=src/jni/jlightgrep.h $(SRC_CLASS_FILES:.class=*.class) $(TEST_CLASS_FILES:.class=*.class) $(noinst_jar_DATA)
