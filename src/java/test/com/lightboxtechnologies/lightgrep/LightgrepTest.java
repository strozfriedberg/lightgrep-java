package com.lightboxtechnologies.lightgrep;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LightgrepTest {
  @Test
  public void createDestroyPatternTest() {
    final PatternHandle hPattern = new PatternHandle();
    try {
      assertNotNull(hPattern);
    }
    finally {
      hPattern.destroy();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void noParsePatternAfterDestroyPatternTest() throws Exception {
    final PatternHandle hPattern = new PatternHandle();
    hPattern.destroy();

    final KeyOptions kopts = new KeyOptions();
    kopts.FixedString = false;
    kopts.CaseInsensitive = false;

    hPattern.parsePattern("meh", kopts);
  }
 
  @Test
  public void doubleDestroyPatternTest() {
    final PatternHandle hPattern = new PatternHandle();
    hPattern.destroy();
    hPattern.destroy();
  }
 
  @Test
  public void parsePatternGoodTest() throws Exception {
    final PatternHandle hPattern = new PatternHandle();
    try {
      final KeyOptions kopts = new KeyOptions();
      kopts.FixedString = false;
      kopts.CaseInsensitive = false;

      final int ret = hPattern.parsePattern("meh", kopts);
      assertTrue(ret != 0);
    }
    finally {
      hPattern.destroy();
    }
  }

  @Test(expected=KeywordException.class)
  public void parsePatternMalformedTest() throws Exception {
    final PatternHandle hPattern = new PatternHandle();
    try {
      final KeyOptions kopts = new KeyOptions();
      kopts.FixedString = false;
      kopts.CaseInsensitive = false;

      hPattern.parsePattern("(xyz", kopts);
    }
    finally {
      hPattern.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void parsePatternNullTest() throws Exception {
    final PatternHandle hPattern = new PatternHandle();
    try {
      final KeyOptions kopts = new KeyOptions();
      kopts.FixedString = false;
      kopts.CaseInsensitive = false;

      hPattern.parsePattern(null, kopts);
    }
    finally {
      hPattern.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void parsePatternNullOptionsTest() throws Exception {
    final PatternHandle hPattern = new PatternHandle();
    try {
      hPattern.parsePattern("meh", null);
    }
    finally {
      hPattern.destroy();
    }
  }

  @Test
  public void createDestroyPatternMapTest() {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      assertNotNull(hPatternMap);
    }
    finally {
      hPatternMap.destroy();
    }
  }

  @Test
  public void doubleDestroyPatternMapTest() {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    hPatternMap.destroy();
    hPatternMap.destroy();
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void createPatternMapNegativeSizeHintTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(-1);
    hPatternMap.destroy();
  }

  @Test(expected=IllegalStateException.class)
  public void noSizeAfterDestroyPatternMapTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    hPatternMap.destroy();

    hPatternMap.size();
  }

  @Test(expected=IllegalStateException.class)
  public void noGetPatternInfoAfterDestroyPatternMapTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    hPatternMap.destroy();

    hPatternMap.getPatternInfo(0);
  }

  @Test(expected=IllegalStateException.class)
  public void noGetUserDataAfterDestroyPatternMapTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    hPatternMap.destroy();

    hPatternMap.getUserData(0);
  }

  @Test(expected=IllegalStateException.class)
  public void noSetUserDataAfterDestroyPatternMapTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    hPatternMap.destroy();

    hPatternMap.setUserData(0, null);
  }

  @Test
  public void createDestroyFSMTest() {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      assertNotNull(hFsm);
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test
  public void doubleDestroyFSMTest() {
    final FSMHandle hFsm = new FSMHandle(0);
    hFsm.destroy();
    hFsm.destroy();
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void createFSMNegativeSizeHintTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(-1);
    hFsm.destroy();
  }

  @Test(expected=IllegalStateException.class)
  public void noAddPatternAfterDestroyFSMTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    hFsm.destroy();

    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      final PatternHandle hPattern = new PatternHandle();
      try {
        hFsm.addPattern(hPatternMap, hPattern, "ASCII");
      }
      finally {
        hPattern.destroy();
      }
    }
    finally {
      hPatternMap.destroy();
    }
  } 

  @Test(expected=IllegalStateException.class)
  public void noCreateProgramAfterDestroyFSMTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    hFsm.destroy();

    final ProgramOptions popts = new ProgramOptions();
    hFsm.createProgram(popts);
  }

  @Test(expected=NullPointerException.class)
  public void addPatternNullPatternMapTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternHandle hPattern = new PatternHandle();
      try {
        hFsm.addPattern(null, hPattern, "ASCII");
      }
      finally {
        hPattern.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void addPatternNullPatternTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try{
        hFsm.addPattern(hPatternMap, null, "ASCII");
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void addPatternNullEncodingTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          hFsm.addPattern(hPatternMap, hPattern, null);
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

// FIMXE: Shouldn't this throw something?
/*
  @Test
  public void addPatternEmptyTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }
*/

  @Test
  public void addPatternGoodTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("(xyzzy)+", kopts);
          final int ret = hFsm.addPattern(hPatternMap, hPattern, "UTF-8");
          assertEquals(0, ret);
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=KeywordException.class)
  public void addPatternEmptyMatchesTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("x*", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

// FIXME: should throw some sort of encoding exception?
  @Test(expected=KeywordException.class)
  public void addPatternBadEncodingTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-13");
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test
  public void sizeEmptyPatternMapTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      assertEquals(0, hPatternMap.size());
    }
    finally {
      hPatternMap.destroy();
    }
  }

  @Test
  public void sizeNonEmptyPatternMapTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("(xyzzy)+", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");

          assertEquals(1, hPatternMap.size());
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }
 
  @Test(expected=IndexOutOfBoundsException.class)
  public void getPatternInfoNegativeIndexTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      hPatternMap.getPatternInfo(-1);
    }
    finally {
      hPatternMap.destroy();
    }
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void getPatternInfoIndexTooLargeTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      hPatternMap.getPatternInfo(42);
    }
    finally {
      hPatternMap.destroy();
    }
  }

  @Test
  public void getPatternInfoIndexJustRightTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          final PatternInfo exp = new PatternInfo(
            "(xyzzy)+", "UTF-8", null
          );

          hPattern.parsePattern(exp.Pattern, kopts);
          hFsm.addPattern(hPatternMap, hPattern, exp.EncodingChain);

          final PatternInfo act = hPatternMap.getPatternInfo(0);
          assertEquals(exp, act);
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void getUserDataNegativeIndexTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      hPatternMap.getUserData(-1);
    }
    finally {
      hPatternMap.destroy();
    }
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void getUserDataIndexTooLargeTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      hPatternMap.getUserData(42);
    }
    finally {
      hPatternMap.destroy();
    }
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void setUserDataNegativeIndexTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      hPatternMap.setUserData(-1, null);
    }
    finally {
      hPatternMap.destroy();
    }
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void setUserDataIndexTooLargeTest() throws Exception {
    final PatternMapHandle hPatternMap = new PatternMapHandle(0);
    try {
      hPatternMap.setUserData(42, null);
    }
    finally {
      hPatternMap.destroy();
    }
  }

  @Test
  public void setUserDataGetUserDataPatternMapTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          final Object[] exp = new Object[]{ null, 43.5, "squamous" };

          hPattern.parsePattern("(xyzzy)+", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");

          hPatternMap.setUserData(0, exp);
          assertEquals(exp, hPatternMap.getUserData(0));
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void createProgramNullOptionsTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      hFsm.createProgram(null);
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=ProgramException.class)
  public void createProgramEmptyTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final ProgramOptions popts = new ProgramOptions();
      hFsm.createProgram(popts);
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test
  public void createProgramGoodTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("(xyzzy)+", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            assertNotNull(hProg);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test
  public void doubleDestroyProgramTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("(xyzzy)+", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          hProg.destroy();
          hProg.destroy();
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void noSizeAfterDestroyProgramTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("(xyzzy)+", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          hProg.destroy();

          hProg.size();
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void noWriteAfterDestroyProgramTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("(xyzzy)+", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          final byte[] buf = new byte[hProg.size()];
          hProg.destroy();

          hProg.write(buf, 0);
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void noCreateContextAfterDestroyProgramTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("(xyzzy)+", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "UTF-8");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          hProg.destroy();

          final ContextOptions copts = new ContextOptions();
          hProg.createContext(copts);
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

// FIXME: output is not the same as it was, get new output
/*
  @Test(expected=IllegalStateException.class)
  public void writeProgramGoodTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final byte[] exp = {
              0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
              0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
              0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
              0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
              0x00, 0x00, 0x00, 0x00, 0x01, 0x6D, 0x00, 0x00,
              0x0B, 0x00, 0x00, 0x00, 0x01, 0x65, 0x00, 0x00,
              0x01, 0x68, 0x00, 0x00, 0x0C, 0x00, 0x00, 0x00,
              0x07, 0x00, 0x00, 0x00, 0x0E, 0x00, 0x00, 0x00,
              0x07, 0x00, 0x00, 0x00
            };

            final int size = hProg.size();
            assertEquals(exp.length, size);

            final byte[] buf = new byte[size];
            hProg.write(buf, 0);

            assertArrayEquals(exp, buf);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test
  public void writeProgramGoodTest() throws Exception {
    final ParserHandle hParser = new ParserHandle(4);
    try {
      final KeyOptions kopts = new KeyOptions();
      kopts.FixedString = false;
      kopts.CaseInsensitive = false;

      hParser.addKeyword("meh", 0, kopts, "ASCII");

      final ProgramOptions popts = new ProgramOptions();
      popts.Determinize = true;        

      final ProgramHandle hProg = hParser.createProgram(popts);
      try {
        final byte[] exp = {
          0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
          0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
          0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
          0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
          0x00, 0x00, 0x00, 0x00, 0x01, 0x6D, 0x00, 0x00,
          0x0B, 0x00, 0x00, 0x00, 0x01, 0x65, 0x00, 0x00,
          0x01, 0x68, 0x00, 0x00, 0x0C, 0x00, 0x00, 0x00,
          0x07, 0x00, 0x00, 0x00, 0x0E, 0x00, 0x00, 0x00,
          0x07, 0x00, 0x00, 0x00
        };

        final int size = hProg.size();
        assertEquals(exp.length, size);

        final byte[] buf = new byte[size];
        hProg.write(buf, 0);

        assertArrayEquals(exp, buf);
      }
      finally {
        hProg.destroy();
      }
    }
    finally {
      hParser.destroy();
    }
  }
*/

  @Test(expected=NullPointerException.class)
  public void writeProgramNullBufferTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            hProg.write(null, 0);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void writeProgramNegativeOffsetTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final byte[] buf = new byte[hProg.size()];
            hProg.write(buf, -1);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void writeProgramOffsetOffEndTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final byte[] buf = new byte[hProg.size()];
            hProg.write(buf, buf.length);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void writeProgramOffsetTooLargeTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final byte[] buf = new byte[hProg.size()];
            hProg.write(buf, 1);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  private static final byte[] aProgram = {
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x01, 0x6D, 0x00, 0x00,
    0x0B, 0x00, 0x00, 0x00, 0x01, 0x65, 0x00, 0x00,
    0x01, 0x68, 0x00, 0x00, 0x0C, 0x00, 0x00, 0x00,
    0x07, 0x00, 0x00, 0x00, 0x0E, 0x00, 0x00, 0x00,
    0x07, 0x00, 0x00, 0x00
  };

  @Test
  public void readWriteProgramGoodTest() {
    final byte[] exp = aProgram;

    final ProgramHandle hProg = ProgramHandle.read(exp, 0, exp.length);
    try {
      final int size = hProg.size();
      assertEquals(exp.length, size);

      final byte[] buf = new byte[size];
      hProg.write(buf, 0);

      assertArrayEquals(exp, buf);
    }
    finally {
      hProg.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void readProgramNullBufferTest() {
    final ProgramHandle hProg = ProgramHandle.read(null, 0, 1);
    hProg.destroy();
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void readProgramOffsetOffEndTest() {
    final byte[] exp = aProgram;
    final ProgramHandle hProg = ProgramHandle.read(exp, exp.length, exp.length);
    hProg.destroy();
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void readProgramOffsetTooLargeTest() {
    final byte[] exp = aProgram;
    final ProgramHandle hProg = ProgramHandle.read(exp, 1, exp.length);
    hProg.destroy();
  }

// FIXME: reading programs has no error-checking, pretty much
/*
  @Test(expected=ProgramException.class)
  public void readProgramGarbageTest() throws UnsupportedEncodingException {
    final byte[] ibuf = "balls balls balls balls balls balls".getBytes("ASCII");
    final ProgramHandle hProg = ProgramHandle.read(ibuf, 0, ibuf.length);
    final byte[] obuf = new byte[hProg.size()];
    hProg.write(obuf, 0);
    System.err.write(obuf, 0, obuf.length);
    hProg.destroy();
  }
*/

  @Test
  public void createContextGoodTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();

            final ContextHandle hCtx = hProg.createContext(copts);
            try {
              assertNotNull(hCtx);
            }
            finally {
              hCtx.destroy();
            }
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void noResetAfterDestroyContextTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();

            final ContextHandle hCtx = hProg.createContext(copts);
            hCtx.destroy();
            hCtx.reset();
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  private static class DummyCallback implements HitCallback {
    public void callback(SearchHit hit) {}
  } 

  @Test(expected=IllegalStateException.class)
  public void noSearchAfterDestroyContextTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();

            final ContextHandle hCtx = hProg.createContext(copts);
            hCtx.destroy();

            final byte[] buf = "a".getBytes("ASCII");
            final HitCallback cb = new DummyCallback();

            hCtx.search(buf, 0, buf.length, 0, cb);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void noCloseoutSearchAfterDestroyContextTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();

            final ContextHandle hCtx = hProg.createContext(copts);
            hCtx.destroy();
        
            final HitCallback cb = new DummyCallback();
            hCtx.closeoutSearch(cb);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void noStartsWithAfterDestroyContextTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();

            final ContextHandle hCtx = hProg.createContext(copts);
            hCtx.destroy();
        
            final byte[] buf = "a".getBytes("ASCII");
            final HitCallback cb = new DummyCallback();

            hCtx.startsWith(buf, 0, buf.length, 0, cb);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test
  public void doubleDestroyContextTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();

            final ContextHandle hCtx = hProg.createContext(copts);
            hCtx.destroy();
            hCtx.destroy();
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void createContextNullOptionsTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            hProg.createContext(null);
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test
  public void resetContextTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("meh", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();
            final ContextHandle hCtx = hProg.createContext(copts);
            try {
              hCtx.reset();
// FIXME: test that we're now at the beginning for new input
            }
            finally {
              hCtx.destroy();
            }
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  private static class HitCollector implements HitCallback {
    public HitCollector(List<SearchHit> l) {
      hits = l;
    }

    public void callback(SearchHit hit) {
      hits.add(hit);
    }

    private final List<SearchHit> hits;
  }

  @Test(expected=NullPointerException.class)
  public void searchArrayNullCallbackTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("a+b", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();
            final ContextHandle hCtx = hProg.createContext(copts);
            try {
              final byte[] buf = "aaabaacabbabcacbaccbbbcbccca".getBytes("ASCII");

              hCtx.search(buf, 0, buf.length, 0, null);
            }
            finally {
              hCtx.destroy();
            }
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  private static class CallbackExploder implements HitCallback {
    public void callback(SearchHit hit) {
      throw new RuntimeException("Out of cheese");
    }
  }

  @Test(expected=RuntimeException.class)
  public void searchArrayBadCallbackTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("a+b", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();
            final ContextHandle hCtx = hProg.createContext(copts);
            try {
              final byte[] buf = "aaabaacabbabcacbaccbbbcbccca".getBytes("ASCII");

              hCtx.search(buf, 0, buf.length, 0, new CallbackExploder());
            }
            finally {
              hCtx.destroy();
            }
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void searchDirectByteBufferNullCallbackTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("a+b", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();
            final ContextHandle hCtx = hProg.createContext(copts);
            try {
              final byte[] arr = "aaabaacabbabcacbaccbbbcbccca".getBytes("ASCII");
              final ByteBuffer buf = ByteBuffer.allocateDirect(arr.length);
              buf.put(arr).flip();

              hCtx.search(buf, arr.length, 0, null);

            }
            finally {
              hCtx.destroy();
            }
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=RuntimeException.class)
  public void searchDirectByteBufferBadCallbackTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("a+b", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();
            final ContextHandle hCtx = hProg.createContext(copts);
            try {
              final byte[] arr = "aaabaacabbabcacbaccbbbcbccca".getBytes("ASCII");
              final ByteBuffer buf = ByteBuffer.allocateDirect(arr.length);
              buf.put(arr).flip();

              hCtx.search(buf, arr.length, 0, new CallbackExploder());
            }
            finally {
              hCtx.destroy();
            }
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=NullPointerException.class)
  public void searchWrappedByteBufferNullCallbackTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("a+b", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();
            final ContextHandle hCtx = hProg.createContext(copts);
            try {
              final byte[] arr = "aaabaacabbabcacbaccbbbcbccca".getBytes("ASCII");
              final ByteBuffer buf = ByteBuffer.wrap(arr);

              hCtx.search(buf, arr.length, 0, null);
            }
            finally {
              hCtx.destroy();
            }
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

  @Test(expected=RuntimeException.class)
  public void searchWrappedByteBufferBadCallbackTest() throws Exception {
    final FSMHandle hFsm = new FSMHandle(0);
    try {
      final PatternMapHandle hPatternMap = new PatternMapHandle(0);
      try {
        final PatternHandle hPattern = new PatternHandle();
        try {
          final KeyOptions kopts = new KeyOptions();
          kopts.FixedString = false;
          kopts.CaseInsensitive = false;

          hPattern.parsePattern("a+b", kopts);
          hFsm.addPattern(hPatternMap, hPattern, "ASCII");

          final ProgramOptions popts = new ProgramOptions();
          popts.Determinize = true;

          final ProgramHandle hProg = hFsm.createProgram(popts);
          try {
            final ContextOptions copts = new ContextOptions();
            final ContextHandle hCtx = hProg.createContext(copts);
            try {
              final byte[] arr = "aaabaacabbabcacbaccbbbcbccca".getBytes("ASCII");
              final ByteBuffer buf = ByteBuffer.wrap(arr);

              hCtx.search(buf, arr.length, 0, new CallbackExploder());
            }
            finally {
              hCtx.destroy();
            }
          }
          finally {
            hProg.destroy();
          }
        }
        finally {
          hPattern.destroy();
        }
      }
      finally {
        hPatternMap.destroy();
      }
    }
    finally {
      hFsm.destroy();
    }
  }

/*
  @Test(expected=NullPointerException.class)
  public void startsWithNullCallbackTest() throws Exception {
    final ParserHandle hParser = new ParserHandle(4);
    try {
      final KeyOptions kopts = new KeyOptions();
      kopts.FixedString = false;
      kopts.CaseInsensitive = false;

      hParser.addKeyword("x+", 0, kopts, "ASCII");

      final ProgramOptions popts = new ProgramOptions();
      popts.Determinize = true;        

      final ProgramHandle hProg = hParser.createProgram(popts);
      try {
        final ContextOptions copts = new ContextOptions();

        final ContextHandle hCtx = hProg.createContext(copts);
        try {
          final byte[] buf = "xxxx".getBytes("ASCII");

          hCtx.startsWith(buf, 0, buf.length, 0, null);
        }
        finally {
          hCtx.destroy();
        }
      }
      finally {
        hProg.destroy();
      }
    }
    finally {
      hParser.destroy();
    }
  }
*/
}
