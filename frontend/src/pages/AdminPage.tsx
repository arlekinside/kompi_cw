import { useEffect, useState } from "react";
import Content from "../components/Content";
import DarkText from "../components/text/DarkText";
import DarkTitle from "../components/text/DarkTitle";
import { Button } from "@mui/material";

interface Data {
    correctness: number;
    usersCount: number;
    health: boolean
}

function AdminPage() {

    const [fetched, setFetched] = useState<boolean>(false);
    const [username, setUsername] = useState<string>('SQLver');
    const [data, setData] = useState<Data>({
        correctness: 100,
        usersCount: 9999,
        health: true
    });

    useEffect(() => {
        if (!fetched) {    
          const fetchData = async () => {
            try {
              const healthResponse = await fetch("/health");
              let health = true
              if (!healthResponse.ok) {
                health = false
              }
    
              const statsResponse = await fetch("/history/stats");
              let correctness = 0;
              if (statsResponse.ok) {
                correctness = (await statsResponse.json()).correctness;
              }
    
              const usersResponse = await fetch("/users/stats");
              let usersCount = 0;
              let username = 'SQLver';
              if (usersResponse.ok) {
                let usersData = await usersResponse.json();
                usersCount = usersData.usersCount;
                username = usersData.username;
              }
    
              setData({
                correctness: correctness,
                usersCount: usersCount,
                health: health,
              });
    
              setUsername(username);
            } catch (error) {
              // Handle errors here
              console.error("Error fetching data:", error);
            }
          };
    
          fetchData();
          setFetched(true);
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
                        alignItems: "center",
                        margin: "30px"
                    }}>
                        <DarkTitle>
                            Admin: <b>{username}</b>
                        </DarkTitle>
                        <div>
                            <DarkText>
                                Users' corectness: <b>{data.correctness}%</b>
                            </DarkText>
                        </div>
                        <div>
                            <DarkText>
                                System available: <b><span style={{color: data.health ? 'Chartreuse' : 'red'}}>{String(data.health)}</span></b>
                            </DarkText>
                        </div>
                        <div>
                            <DarkText>
                                Number of users: <b>{data.usersCount}</b>
                            </DarkText>
                        </div>
                        <a href="/">
                            <Button size={"large"} variant="outlined" style={{margin: "3vh"}}>
                                Back
                            </Button>
                        </a>
                    </div>
            </Content>
        </div>
    );
}

export default AdminPage;