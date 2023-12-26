import { Button, FormControlLabel, Radio, RadioGroup } from "@mui/material";
import { useEffect, useState } from "react";
import Params from "../Params";
import Content from "../components/Content";
import DarkText from "../components/text/DarkText";
import DarkTitle from "../components/text/DarkTitle";

interface FormInput {
    query: string;
    queryType: string;
}

interface Request {
    query: string;
    queryType: string;
}

interface Response {
    qeury: string;
    quryType: string;
    valid: boolean;
    comment: string;
}

function VerifyPage() {
    
    const [formInput, setFormInput] = useState<FormInput>({
        query: '',
        queryType: 'select'
    });

    const [sqlCorrect, setSqlCorrect] = useState<boolean>(false);
    const [sqlWrong, setSqlWrong] = useState<boolean>(false);
    const [sqlComment, setSqlComment] = useState<string|undefined>();

    const resetStates = () => {
        setSqlCorrect(false);
        setSqlWrong(false);
        setSqlComment(undefined);
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        resetStates();

        let query = formInput.query;
        let queryType = formInput.queryType;

        if(query.trim().length === 0) {
            return;
        }

        let req: Request = {
            query: query,
            queryType: queryType
        }

        fetch(`/sql`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify(req)
        }).then(res => {
            if(!res.ok) {
                console.log('NOK response returned');
            }
            return res.json();
        }).then((data : Response) => {
            if (data.valid) {
                setSqlCorrect(true);
            } else {
                setSqlWrong(true);
            }
            if (data.comment) {
                setSqlComment(data.comment);
                window.scrollTo(0, document.body.scrollHeight)
            }
        }).catch(e => {
            console.error('Error while fetching', e);
            alert('Error connecting to server');
        })
    };

    const handleTextAreaChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormInput({
            ...formInput,
            [name]: value,
        });
    };

    const handleRadioChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormInput({
            ...formInput,
            [name]: value,
        });
    };

    useEffect(() => {
        if (sqlComment) {
            window.scrollTo(0, document.body.scrollHeight)
        } else {
            window.scrollTo(0, 0)
        }
    }, [sqlComment])

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "flex-start",
            minWidth: "100%",
            alignItems: "center"
        }}>
            <Content>
                <form onSubmit={handleSubmit}>
                    <div style={{
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "flex-start",
                        minWidth: "100%",
                        alignItems: "center"
                    }}>
                        <DarkTitle>
                            SQLver
                        </DarkTitle>
                        <DarkText>
                            What to verify
                        </DarkText>
                        <textarea name='query' id='query' onChange={handleTextAreaChange} rows={10} style={{
                            minWidth: "60%",
                            maxWidth: "60%",
                            borderRadius: "15px",
                            padding: "10px",
                            backgroundColor: Params.colors.main,
                            color: "#FFFFFF",
                            resize: "none",
                            fontSize: "1vw",
                            outline: sqlCorrect ? '3px solid Chartreuse' : sqlWrong ? '3px solid red' : 'none'
                        }}/>
                        <DarkText>
                            How to verify
                        </DarkText>
                        <RadioGroup defaultValue="select" row>
                        <FormControlLabel label="Select" control={<Radio name="queryType" value="select" onChange={handleRadioChange}/>} style={{color: Params.colors.main}}/>
                        <FormControlLabel label="Update" control={<Radio name="queryType" value="update" onChange={handleRadioChange}/>} style={{color: Params.colors.main}}/>
                        <FormControlLabel label="Insert" control={<Radio name="queryType" value="insert" onChange={handleRadioChange}/>} style={{color: Params.colors.main}}/>
                        <FormControlLabel label="Delete" control={<Radio name="queryType" value="delete" onChange={handleRadioChange}/>} style={{color: Params.colors.main}}/>
                        </RadioGroup>
                        <div>
                            <Button size={"large"} type={"submit"} variant="contained" style={{margin: "3vh"}}>
                                Verify
                            </Button>
                            <a href="/home">
                                <Button size={"large"} variant="outlined" style={{margin: "3vh"}}>
                                    Home
                                </Button>
                            </a>
                        </div>
                    </div>
                </form>
            </Content>
            {(sqlComment) && 
                <Content>
                    <div style={{
                        padding: '15px',
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "flex-start",
                        minWidth: "100%",
                        alignItems: "center"
                    }}>
                        <DarkTitle>Comments</DarkTitle>
                        <DarkText>{sqlComment}</DarkText>
                    </div>
                </Content>} 
        </div>
    );
}

export default VerifyPage;