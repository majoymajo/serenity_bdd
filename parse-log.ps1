$log = ".\mvn-q-clean-test.log"
$lines = Get-Content $log
# 1) First Selenium exception OR assertion-like error
$errPatterns = @(
  '^org\.openqa\.selenium\..*Exception:',
  '^java\.lang\.AssertionError:',
  '^org\.junit\.(ComparisonFailure|AssertionError):',
  '^org\.opentest4j\..*Error:'
)
$errMatch = $null
foreach ($p in $errPatterns) {
  $m = $lines | Select-String -Pattern $p | Select-Object -First 1
  if ($m) { $errMatch = $m; break }
}
if ($errMatch) {
  $i = $errMatch.LineNumber - 1
  $msg1 = $lines[$i].TrimEnd()
  $msg2 = ($lines | Select-Object -Skip ($i+1) | Where-Object { $_ -ne '' } | Select-Object -First 1)
  $msg2 = if ($msg2) { $msg2.Trim() } else { '' }
  $err = if ($msg2 -and $msg2 -notmatch '^at\s') { "$msg1 $msg2" } else { $msg1 }
} else {
  $err = '<not found>'
}

# 2) First failing step text
$stepMatch = $lines | Select-String -Pattern 'Test failed at step:\s*(.*)$' | Select-Object -First 1
$step = if ($stepMatch) { $stepMatch.Matches[0].Groups[1].Value.Trim() } else { '<not found>' }

# 3) Final Surefire summary line
$sumMatch = $lines | Select-String -Pattern 'Tests run:\s*\d+, Failures:\s*\d+, Errors:\s*\d+, Skipped:\s*\d+' | Select-Object -Last 1
$sumLine = if ($sumMatch) { $sumMatch.Line.Trim() } else { '<not found>' }

"Failing step: $step"
"Exception/assertion: $err"
"Surefire summary: $sumLine"
