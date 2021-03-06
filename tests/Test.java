package tests;

import sounds.*;
import processing.Process;

import static org.junit.Assert.*;
import java.util.*;

public class Test{
	
	// Instance variables for use with testing
	Sound        aSoundA      = new Sound(100, 100, 100, 100, 100, 100);
	Note         aNoteA       = new Note("A", 440, 60, 1, 1, 127, 1);
	Note         aNoteB       = new Note("B", 440, 60, 1, 1, 127, 1);
	Note         aNoteC       = new Note("C", 450, 61, 2, 4, 125, 6);
	Note         aNoteD       = new Note("D", 440, 60, 1, 1, 127, 1);
	Note         aNoteE       = new Note("E", 460, 62, 3, 5, 126, 7);
	Note         aNoteF       = new Note("F", 440, 60, 1, 1, 127, 1);
	Note         aNoteG       = new Note("G", 470, 66, 6, 6, 6, 6);
	Note[]       aNoteArray   = {aNoteA, aNoteC, aNoteE};
	Note[]       aNoteArray2  = {aNoteA, aNoteB, aNoteC, aNoteD, aNoteE, aNoteF, aNoteG};
	String[]     aStrNoteA    = {"A", "B", "C", "D", "E", "F", "G"};
	List<Note>   aNoteList    = Arrays.asList(aNoteA, aNoteC, aNoteE);
	List<Note>   aNoteList2   = Arrays.asList(aNoteA, aNoteB, aNoteC, aNoteD, aNoteE, aNoteF, aNoteG);
	List<Note>   aNoteList3   = Arrays.asList(aNoteC, aNoteE, aNoteG);
	List<String> aPool        = Arrays.asList("A", "B", "C", "D");
	String[]     aPool2       = {"Ab", "A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G"};
	List<String> aPool3       = new ArrayList<String>(Arrays.asList(aPool2));
	// Copy constructor test
	NoteCollection  aNoteCollection1  = new NoteCollection(aNoteList);
	NoteCollection  aNoteCollection3  = new NoteCollection(aNoteList2); 
	Sound        aSoundB      = new Sound(aSoundA);
	Note         aNoteXX      = new Note(aNoteA);
	// End of copy constructor declarations
	Chord        aChorda      = new Chord(aNoteList);
	Scale        aScalea      = new Scale(aNoteList2);
	NoteCollection    aNoteCollection2  = new NoteCollection(aNoteCollection1);
	Chord        aChordb      = new Chord(aChorda);
	Scale        aScaleb      = new Scale(aScalea);
	Chord        aChord1      = new Chord(aNoteArray, "Am");
	Chord        aChord2      = new Chord(aNoteArray);
	Chord        aChord3      = new Chord(aNoteList3);
	Scale        aScale1      = new Scale(aNoteList, "A aeolian");
	Scale        aScale2      = new Scale(aNoteList);
	Scale        aScale3      = new Scale(aNoteArray);
	Scale        aScale4      = new Scale(aStrNoteA);
	List<Chord>  aChList      = new ArrayList<Chord>(Arrays.asList(aChord1, aChord2));
	ChordCollection   aChG1   = new ChordCollection(aChList);
	Harmony      aHarmony1    = new Harmony(aChList, "a harmony");
	// Copy constructor tests
	ChordCollection   aChG2   = new ChordCollection(aChG1);
	Harmony      aHarmony2    = new Harmony(aHarmony1);
	// End of copy constructor declarations
	// Instances
	Process      aProcess   = new Process();
	Formulas     formulas   = new Formulas();

	public void runTests(){		
		// Class Tests ---------------------------------
		assertEquals(aNoteA.getName(), "A");
		assertEquals(aScale1.getName(), "A aeolian");
		assertEquals(aChord1.getName(), "Am");
		assertEquals(aChord2.getName(), "ACE");
		assertEquals(aScale2.getName(), "ACE");
		assertEquals(aScale3.getName(), "ACE");
	    assertEquals(aScale4.getName(), "ABCDEFG");
		assertEquals(aChG1.getChordsAsString(), Arrays.toString(new String[] {"Am", "ACE"}));
		
		// Copy constructor tests
		aNoteCollection2.addNote(aNoteB);
		assertEquals(aNoteCollection2.getNotesAsString(), Arrays.toString(new String[] {"A", "C", "E", "B"}));
		assertEquals(aNoteCollection1.getNotesAsString(), Arrays.toString(new String[] {"A", "C", "E"}));
		aChordb.addNote(aNoteC);
		assertEquals(aChordb.getNotesAsString(), Arrays.toString(new String[] {"A", "C", "E", "C"}));
		assertEquals(aChorda.getNotesAsString(), Arrays.toString(new String[] {"A", "C", "E"}));
		assertEquals(aSoundA.toString(), aSoundB.toString());
		assertEquals(aNoteA.toString(), aNoteXX.toString());
		assertEquals(aHarmony1.toString(), aHarmony2.toString());
		assertEquals(aChG1.toString(), aChG2.toString());
		
		// Chord invert(), NoteGoup rotateNotes(), and originalNotes: aChord3 initial state = C E G
		// Tests for inversion dynamics and originalNotes not changing
		aChord3.invert();
		assertEquals(aChord3.getNotesAsString(),         Arrays.toString(new String[] {"G", "C", "E"}));
		assertEquals(aChord3.getOriginalNotesAsString(), Arrays.toString(new String[] {"C", "E", "G"}));
		aChord3.invert();
		assertEquals(aChord3.getNotesAsString(),         Arrays.toString(new String[] {"E", "G", "C"}));
		assertEquals(aChord3.getOriginalNotesAsString(), Arrays.toString(new String[] {"C", "E", "G"}));
		aChord3.invert();
		assertEquals(aChord3.getNotesAsString(),         Arrays.toString(new String[] {"C", "E", "G"}));
		assertEquals(aChord3.getOriginalNotesAsString(), Arrays.toString(new String[] {"C", "E", "G"}));
		aChord3.invert(-1);
		assertEquals(aChord3.getNotesAsString(),         Arrays.toString(new String[] {"E", "G", "C"}));
		aChord3.invert(-1);
		assertEquals(aChord3.getNotesAsString(),         Arrays.toString(new String[] {"G", "C", "E"}));
		
		// Test for reset
		aChord3.reset();
		assertEquals(aChord3.getNotesAsString(), Arrays.toString(new String[] {"C", "E", "G"}));
		
		// Tests for inversions based on originalNotes
		aChord3.secondInversion();
		assertEquals(aChord3.getNotesAsString(),         Arrays.toString(new String[] {"E", "G", "C"}));
		aChord3.firstInversion();
		assertEquals(aChord3.getNotesAsString(),         Arrays.toString(new String[] {"G", "C", "E"}));
		aChord3.reset();
		assertEquals(aChord3.getNotesAsString(),         Arrays.toString(new String[] {"C", "E", "G"}));
		
		// toString(); tests
		assertEquals(aNoteA.toString(), "name: A frequency: 440.0 velocity: 60 attack: 1 decay: 1 sustain: 127 release: 1 isActive: true");
		assertEquals(aSoundA.toString(), "frequency: 100.0 velocity: 100 attack: 100 decay: 100 sustain: 100 release: 100 isActive: true");
		assertEquals(aNoteCollection1.toString(), "size: 3 notes: [A, C, E] originalNotes: [A, C, E]name: ACE secondaryName: null");
		aNoteCollection1.setSecondaryName("a secondary name"); // sets a secondary name for aNoteCollection1
		assertEquals(aNoteCollection1.toString(), "size: 3 notes: [A, C, E] originalNotes: [A, C, E]name: ACE secondaryName: a secondary name");
		assertEquals(aChG1.toString(), "chords: [Am, ACE] size: 2");
		assertEquals(aHarmony1.toString(), "name: a harmony chords: [Am, ACE] size: 2");
		
		// Process Tests -------------------------------
		
		// stepCount()
		assertEquals(aProcess.stepCount("C","Eb"), 3);
		assertEquals(aProcess.stepCount(aNoteA, aNoteC), 3);
		assertEquals(aProcess.stepCount(aNoteG, aNoteA), 2);
		assertEquals(aProcess.stepCount("C","Eb", aPool3), 3);
		assertEquals(aProcess.stepCount("Gb", "B", aPool2), 5);
		assertEquals(aProcess.stepCount(aNoteA, aNoteC, aPool), 2);
		assertEquals(aProcess.stepCount(aNoteA, aNoteC, aPool2), 3);

		// scalize()
		assertEquals(aProcess.scalize(aNoteA, formulas.getAeolian()).getNotesAsString(),         "[A, B, C, D, E, F, G]");	     
		assertEquals(aProcess.scalize(aNoteA, formulas.getAeolian(), aPool2).getNotesAsString(), "[A, B, C, D, E, F, G]");	     
		assertEquals(aProcess.scalize(aNoteC, formulas.getIonian()).getNotesAsString(),          "[C, D, E, F, G, A, B]");	     
		assertEquals(aProcess.scalize(aNoteC, formulas.getIonian(), aPool).getNotesAsString(),   "[C, A, C, D, B, D, B]");
		assertEquals(aProcess.scalize("F", formulas.getIonian()).getNotesAsString(),             "[F, G, A, Bb, C, D, E]");
		assertEquals(aProcess.scalize("D", formulas.getAeolian(), aPool2).getNotesAsString(),    "[D, E, F, G, A, Bb, C]");
		
		// harmonize() & scalize()
		assertEquals(aProcess.harmonize(aProcess.scalize(aNoteA, formulas.getAeolian())).getChordsAsString(), 
				     "[ACE, BDF, CEG, DFA, EGB, FAC, GBD]");
		assertEquals(aProcess.harmonize(aProcess.scalize(aNoteC, formulas.getIonian()), 4).getChordsAsString(), 
				     "[CEGB, DFAC, EGBD, FACE, GBDF, ACEG, BDFA]");
		assertEquals(aProcess.harmonize(aProcess.scalize(aNoteC, formulas.getIonian()), 5).getChordsAsString(),
				     "[CEGBD, DFACE, EGBDF, FACEG, GBDFA, ACEGB, BDFAC]");
		assertEquals(aProcess.harmonize(aProcess.scalize(aNoteC, formulas.getIonian()), 6).getChordsAsString(),
			         "[CEGBDF, DFACEG, EGBDFA, FACEGB, GBDFAC, ACEGBD, BDFACE]");
		assertEquals(aProcess.harmonize(aProcess.scalize(aNoteC, formulas.getIonian()), 7).getChordsAsString(),
		             "[CEGBDFA, DFACEGB, EGBDFAC, FACEGBD, GBDFACE, ACEGBDF, BDFACEG]");
		
		// harmonize()
		assertEquals(aProcess.harmonize(aStrNoteA).getChordsAsString(), 
				     Arrays.toString(new String[] {"ACE", "BDF", "CEG", "DFA", "EGB", "FAC", "GBD"}));
		assertEquals(aProcess.harmonize(aNoteList2).getChordsAsString(),
				     Arrays.toString(new String[] {"ACE", "BDF", "CEG", "DFA", "EGB", "FAC", "GBD"}));
		assertEquals(aProcess.harmonize(aNoteList2, 4).getChordsAsString(),
			         Arrays.toString(new String[] {"ACEG", "BDFA", "CEGB", "DFAC", "EGBD", "FACE", "GBDF"}));
		assertEquals(aProcess.harmonize(aNoteArray2, 4).getChordsAsString(),
		             Arrays.toString(new String[] {"ACEG", "BDFA", "CEGB", "DFAC", "EGBD", "FACE", "GBDF"}));
		assertEquals(aProcess.harmonize(aNoteArray2).getChordsAsString(),
		             Arrays.toString(new String[] {"ACE", "BDF", "CEG", "DFA", "EGB", "FAC", "GBD"}));
		
		// iterators
		NoteCollection aNoteCollection3  = new NoteCollection(aNoteA, aNoteB, aNoteC);
		String         aStringForTest1   = "";
		for (Note e : aNoteCollection3) aStringForTest1 += " " + e.getName();
		assertEquals(" A B C", aStringForTest1);
		ChordCollection aChordCollection3 = new ChordCollection(aChord1, aChord2, aChord3);
		String          aStringForTest2   = "";
		for (Chord e : aChordCollection3)  aStringForTest2 += " " + e.getName();
		assertEquals(" Am ACE CEG", aStringForTest2);

		// :
		System.out.println("All test passed! :D :D :D");
	}
	
	public static void main(String[] args){
		Test theTests = new Test();
		theTests.runTests();
	}
}