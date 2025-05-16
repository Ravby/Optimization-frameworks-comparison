function WriteResultsToFile(name, results)
projectDirectory = pwd;
projectDirFile = java.io.File(projectDirectory);
parentDirFile = projectDirFile.getParentFile().getParentFile().getParentFile();
fileLocation = fullfile(string(parentDirFile), 'EARS comparison', 'Algorithm results', name + '.txt');
%fileLocation = fullfile(filesDir, [name, '.txt']);
file = java.io.File(fileLocation);
directory = file.getParent();
if isempty(directory)
    directory = pwd;
    fileLocation = fullfile(directory, fileLocation);
    file = java.io.File(fileLocation);
end
fileDirectory = java.io.File(directory);
fileDirectory.mkdirs();
file.createNewFile();
try
    bw = java.io.BufferedWriter(java.io.OutputStreamWriter(java.io.FileOutputStream(file, false)));
    for i = 1:numel(results)
        bw.write(num2str(results(i)));
        bw.newLine();
    end
    bw.close();
catch ME
    ME.stack
end
end