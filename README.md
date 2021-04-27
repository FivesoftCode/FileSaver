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

### License

        The MIT License (MIT)

        Copyright (c) 2021 FivesoftCode

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.
