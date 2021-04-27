# FileSaver
Save files easily. No permissions required.

### Usage

Implementation:

    implementation 'com.github.FivesoftCode:FileSaver:1.0.0'
    
Save file:

    FileSaver.from(this)
                    .setFile("Hello world!".getBytes())
                    .setName("test.txt")
                    .setType("plain/text")
                    .setListener((fileLocation, resCode) -> {
                        if(resCode == OnResultsListener.OK){
                            //File saved!
                        } else if(resCode == OnResultsListener.CANCELED_BY_USER){
                            //User canceled saving file.
                        } else {
                            //Error occured.
                        }
                    })
                    .save();
