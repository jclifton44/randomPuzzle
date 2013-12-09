#/user/lib/perl

open FILE_IN, "<", "txt.file" or die;
@lines = <FILE_IN>;
close FILE_IN;
foreach(@lines) {
	if($? != "") {
		open FILE_OUT, ">", "txt.file", or die;
		print FILE_OUT "";
		close FILE_OUT;
	}
}
open FILE_OUT, ">", "txt.file" or die $!;
my @randomBears;
$numOfBears = 50000;
@maleBears;
@femaleBears;
@allBears;
for( $i = 0; $i < $numOfBears; $i++ ) {
	if($i < $numOfBears/2) {
		push(@femaleBears,"B_".$i);
	}else{
		push(@maleBears,"B_".$i);
	}		
}	
push(@allbears,@maleBears);
push(@allbears,@femaleBears);
for($i = 0; $i < 10; $i++ ) {
	$bearid = int(rand($numOfBears));
	$randBear1 = @allbears[$bearid];
	$mombearid = int(rand($numOfBears/2));
	$dadbearid = int(rand($numOfBears/2));
	$randBear2 = @femaleBears[$mombearid];
	$randBear3 = @maleBears[$dadbearid];
	print(@maleBears[30000]);
	$sex = $bearid < $numOfBears/2  ? "M" : "F";
	$age = int(rand(30)) / 10;
	$bearLine = $randBear1 . " : " . $sex . " : " . $randBear2 . " : " . $randBear3 . " : " . $age . "\n";
	print $bearLine;
	print FILE_OUT $bearLine;

}
	close FILE_OUT;
	

print('hihihi');
