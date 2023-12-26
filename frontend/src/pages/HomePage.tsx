import { Button, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import Content from "../components/Content";
import DarkTitle from "../components/text/DarkTitle";
import DarkText from "../components/text/DarkText";

interface FormInput {
    webhookUrl: string
}

interface History {
    id: number;
    valid: boolean;
    query: string;
    date: string;
}

interface Response {
    username: string,
    histories: History[],
    webhookUrl: string
}

function HomePage() {

    const [username, setUsername] = useState<string>('SQLver');
    const [histories, setHistories] = useState<History[]>();
    const [webhookUrl, setWebhookUrl] = useState<string>();
    const [fetched, setFetched] = useState<boolean>(false);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        fetch('/users/webhook', {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            body: JSON.stringify({
                webhookUrl: webhookUrl
            })
        }).then(res => {
            if (!res.ok) {
                alert('Error updating the webhook url')
            }
        }).catch(e => {
            alert('Error updating the webhook url')
        })

    }

    const onChangeWebhook = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { value } = e.target;
        setWebhookUrl(value);
    };

    useEffect(() => {
        if (!fetched) {
            setFetched(true);

            const fetchData = async () => {
                try {
                    let response = await fetch(`/users`, {
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        }});

                    if (!response.ok) {
                        throw new Error('Error loading history');
                    }

                    const data: Response = await response.json();
                    setUsername(data.username);
                    setHistories(data.histories);
                    setWebhookUrl(data.webhookUrl);
                } catch (error) {
                    console.error('Error while fetching data:', error);
                    alert('Error loading data');
                }
            };

            fetchData();
        }
    }, [fetched]);
    

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "flex-start",
            minWidth: "100%",
            alignItems: "center"
        }}>
            <Content>
                <div style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "flex-start",
                    minWidth: "100%",
                    alignItems: "center"
                }}>
                    <DarkTitle>{username}</DarkTitle>
                    <div>
                        <a href="/">
                            <Button size={"large"} variant="outlined" style={{margin: "3vh"}}>
                                Back
                            </Button>
                        </a>
                        <a href="/logout">
                            <Button size={"large"} variant="outlined" style={{margin: "3vh"}}>
                                Logout
                            </Button>
                        </a>
                    </div>
                    <form onSubmit={handleSubmit} style={{
                        margin: "30px",
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "flex-start",
                        alignItems: "center"
                    }}>
                        <DarkText>Webhook URL</DarkText>
                        <TextField type="url" value={webhookUrl} onChange={onChangeWebhook} sx={{minWidth: "500px", margin: "10px"}}></TextField>
                        <Button size={"large"} variant="contained" style={{margin: "3vh"}} type="submit">
                            Update
                        </Button>
                    </form>
                    <DarkText>History</DarkText>
                    <Table sx={{maxWidth: "80%", minWidth: "80%", margin: "30px"}}>
                        <TableHead>
                            <TableRow>
                                <TableCell>Valid</TableCell>
                                <TableCell>Query</TableCell>
                                <TableCell align="right">Date</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {histories?.map(row => (
                                <TableRow key={row.id}>
                                    <TableCell>{String(row.valid)}</TableCell>
                                    <TableCell>{String(row.query)}</TableCell>
                                    <TableCell align="right">{String(row.date)}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </div>
            </Content>
        </div>
    );
}

export default HomePage;