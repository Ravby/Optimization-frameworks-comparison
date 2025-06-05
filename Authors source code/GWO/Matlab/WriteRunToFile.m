function WriteRunToFile(name, improvements)
    % Construct file path
    projectDirectory = pwd;
    projectDirFile = java.io.File(projectDirectory);
    parentDirFile = projectDirFile.getParentFile().getParentFile().getParentFile();
    fileLocation = fullfile(string(parentDirFile), 'EARS comparison', 'Algorithm results', 'Runs', name + ".csv");
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
    
    % Write to CSV file
    try
        bw = java.io.BufferedWriter(java.io.OutputStreamWriter(java.io.FileOutputStream(file, false)));
        % Write header
        bw.write('Evaluations,Fitness');
        bw.newLine();
        % Write improvements
        for i = 1:size(improvements, 1)
            bw.write(sprintf('%d,%.8e', improvements(i, 1), improvements(i, 2)));
            bw.newLine();
        end
        bw.close();
    catch ME
        disp('Error writing to file:');
        disp(ME.message);
        rethrow(ME);
    end
end